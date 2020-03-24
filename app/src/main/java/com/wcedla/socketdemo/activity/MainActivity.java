package com.wcedla.socketdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wcedla.socketdemo.R;
import com.wcedla.socketdemo.data.SocketData;
import com.wcedla.socketdemo.utils.SystemUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "wcedlaLog";
    EditText ipInput;
    EditText portInput;
    CheckBox hexSelect;
    Button sendData;
    EditText sendInput;
    TextView receiveContent;
    TextView sendClear;
    TextView receiveClear;
    TextView paraSelect;
    TextView dataAnalysis;
    volatile boolean stopReceive = false;
    boolean isHexShow = true;
    boolean receiveData = false;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 2000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("SocketThread");
            return thread;
        }
    });

    byte[] sendDataBuf;
    String ip;
    int port;
    DatagramSocket datagramSocket;
    byte[] receiveBytes;
    DatagramPacket receiverPacket;
    String receiveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initListener();
    }

    private void initData() {
        ipInput = findViewById(R.id.ipInput);
        portInput = findViewById(R.id.portInput);
        hexSelect = findViewById(R.id.hexSelect);
        sendData = findViewById(R.id.sendButton);
        sendInput = findViewById(R.id.sendInput);
        receiveContent = findViewById(R.id.receiveContent);
        sendClear = findViewById(R.id.sendClear);
        receiveClear = findViewById(R.id.receiveClear);
        paraSelect = findViewById(R.id.paraSelect);
        dataAnalysis = findViewById(R.id.dataAnalysis);
        SystemUtils.setInputFilter(ipInput, "^[0-9.]+$", 15);
        SystemUtils.setInputFilter(portInput, "^[0-9]+$", 7);
        SystemUtils.setInputFilter(sendInput, "^[A-Fa-f0-9 ]+$", 5000);
        ipInput.setText(SocketData.IP);
        portInput.setText(SocketData.PORT + "");
        //读取ARM版本号
        sendInput.setText("26 00 00 00 00 00 00 00 00 00 00 00 03 02 00 38 20 57 bb");
    }

    private void initListener() {
        sendClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInput.setText("");
            }
        });
        receiveClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveContent.setText("");
                dataAnalysis.setVisibility(View.GONE);
            }
        });
        sendData.setOnClickListener(v -> {
            String portText = portInput.getText().toString();
            String inputTxt = sendInput.getText().toString();
            ip = ipInput.getText().toString();
            port = Integer.parseInt(portText);
            if (!TextUtils.isEmpty(inputTxt) && !TextUtils.isEmpty(portText) && !TextUtils.isEmpty(ip)) {
                if (!isHexShow) {
                    sendDataBuf = sendInput.getText().toString().getBytes();
                } else {
                    sendDataBuf = getHexData(sendInput.getText().toString());
                }
                sendData();
            } else {
                Toast.makeText(MainActivity.this, "请检查数据输入是否正确！", Toast.LENGTH_SHORT).show();
            }
        });
        hexSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isHexShow = true;
                } else {
                    isHexShow = false;
                }
            }
        });
        paraSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parameterSelectIntent = new Intent(MainActivity.this, ParameterSelectActivity.class);
                startActivityForResult(parameterSelectIntent, 6336);
            }
        });
        dataAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dataAnalysisIntent = new Intent(MainActivity.this, ReadDataAnalysisActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("receiveData", receiveText);
                dataAnalysisIntent.putExtras(bundle);
                startActivity(dataAnalysisIntent);
            }
        });
    }

    private void sendData() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //UDP
                try {
                    if (datagramSocket == null) {
                        datagramSocket = new DatagramSocket(port);
                    }
                    InetAddress droneAddress = InetAddress.getByName(ip);
                    DatagramPacket datagramPacket = new DatagramPacket(sendDataBuf, sendDataBuf.length, droneAddress, port);
                    datagramSocket.send(datagramPacket);
                    Log.d(TAG, "数据已发送！");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "发送成功!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "发送失败!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                Thread nowThread = Thread.currentThread();
                Log.d(TAG, "当前线程:" + nowThread.getName());
                if (nowThread.getName().equals("SocketThread")) {
                    nowThread.interrupt();
                }
                //开启线程接收数据
                if (!receiveData) {
                    startReceiveData();
                }
            }
        });
    }

//    private void startReceiveData() {
//        threadPoolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                receiveData = true;
//                while (!stopReceive) {
//                    //接收数据,使用DatagramPacket，用来接收长度为 length 的数据包。
//                    receiveBytes = new byte[2000];
//                    receiverPacket = new DatagramPacket(receiveBytes, receiveBytes.length);
//                    try {
//                        datagramSocket.receive(receiverPacket);
//                        if (!isHexShow) {
//                            receiveText = new String(receiveBytes, Charset.forName("GB2312"));
//                            Log.d(TAG, "接受到的数据(10进制转字符显示):" + receiveText);
//                        } else {
//                            receiveText = bytes2HexString(receiveBytes);
////                        StringBuilder stringBuilder = new StringBuilder();
////                        for (byte b : receiveBytes) {
////                            stringBuilder.append(Integer.toHexString(b&0xff));
////                            stringBuilder.append(" ");
////                        }
////                        receiveText = stringBuilder.toString();
//                            Log.d(TAG, "接收到的数据转十六进制显示:" + receiveText);
//                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                receiveContent.setText(receiveText);
//                                dataAnalysis.setVisibility(View.VISIBLE);
//                            }
//                        });
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                receiveData = false;
//                Thread.currentThread().interrupt();
//            }
//        });
//    }

    byte[] resultBytes=null;

    private void startReceiveData() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                receiveData = true;
                //接收数据,使用DatagramPacket，用来接收长度为 length 的数据包。
                receiveBytes = new byte[2000];
                while (!stopReceive) {
                    if(receiverPacket==null) {
                        receiverPacket = new DatagramPacket(receiveBytes, receiveBytes.length);
                    }
                    try {
                        datagramSocket.receive(receiverPacket);
                        resultBytes=subByte(receiverPacket.getData(),8,receiverPacket.getLength()-8);
                        if (!isHexShow) {
                            receiveText = new String(resultBytes, Charset.forName("GB2312"));
                            Log.d(TAG, "接受到的数据(10进制转字符显示):" + receiveText);
                        } else {
                            receiveText = bytes2HexString(resultBytes);
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for (byte b : receiveBytes) {
//                            stringBuilder.append(Integer.toHexString(b&0xff));
//                            stringBuilder.append(" ");
//                        }
//                        receiveText = stringBuilder.toString();
                            Log.d(TAG, "接收到的数据转十六进制显示:" + receiveText);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                receiveContent.setText(receiveText);
                                dataAnalysis.setVisibility(View.VISIBLE);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                receiveData = false;
                Thread.currentThread().interrupt();
            }
        });
    }

    /**
     * 截取byte数组
     *
     * @param src
     * @param startIndex
     * @param length
     * @return
     */
    public static byte[] subByte(byte[] src, int startIndex, int length) {
        byte[] des = new byte[length];
        int i = 0;
        for (int j = startIndex; i < length; ++j) {
            des[i] = src[j];
            ++i;
        }
        return des;
    }

    private byte[] getHexData(String originData) {
        String[] commandArray = originData.split(" ");
        byte[] commandBytes = new byte[commandArray.length];
        int index = 0;
        for (String command : commandArray) {
            //将传输的16进制转为10进制传输，服务器收到数据进行转换
            commandBytes[index] = (byte) Integer.parseInt(command, 16);
            index += 1;
        }
        for (byte b : commandBytes) {
            Log.d(TAG, "转换后的值(10进制表示):" + b);
        }
        return commandBytes;
    }

    public int getCrc16(byte[] bytes, boolean needChange) {
        int crc = 0xFFFF;
        int polynomial = 0xA001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            crc ^= bytes[i] & 0xFF;
            for (j = 0; j < 8; j++) {
                if ((crc & 1) != 0) {
                    crc >>= 1;
                    crc ^= polynomial;
                } else {
                    crc >>= 1;
                }
            }
        }
        //高低位转换，看情况使用
        if (needChange) {
            crc = ((crc & 0x0000FF00) >> 8) | ((crc & 0x000000FF) << 8);
        }
        return crc;
    }

    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (byte value : b) {
            String hex = Integer.toHexString(value & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase() + " ";
        }

        return r;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 6336) {
            if (data != null && data.getExtras() != null) {
                String sendData = data.getExtras().getString("sendData");
                sendInput.setText(sendData);
            }
        }
    }


    @Override
    protected void onDestroy() {
        if (datagramSocket != null) {
            datagramSocket.close();
        }
        stopReceive = true;
        threadPoolExecutor.shutdownNow();
        super.onDestroy();
    }

    //    public static int getCrc16(byte[] bytes) {
//        int len = bytes.length;
//        // 预置 1 个 16 位的寄存器为十六进制FFFF, 称此寄存器为 CRC寄存器。
//        int crc = 0xFFFF;
//        int i, j;
//        for (i = 0; i < len; i++) {
//            // 把第一个 8 位二进制数据 与 16 位的 CRC寄存器的低 8 位相异或, 把结果放于 CRC寄存器
//            crc = ((crc & 0xFF00) | (crc & 0x00FF) ^ (bytes[i] & 0xFF));
//            for (j = 0; j < 8; j++) {
//                // 把 CRC 寄存器的内容右移一位( 朝低位)用 0 填补最高位, 并检查右移后的移出位
//                if ((crc & 0x0001) > 0) {
//                    // 如果移出位为 1, CRC寄存器与多项式A001进行异或
//                    crc = crc >> 1;
//                    crc = crc ^ 0xA001;
//                } else {
//                    // 如果移出位为 0,再次右移一位
//                    crc = crc >> 1;
//                }
//            }
//        }
//        //高低位转换，看情况使用（譬如本人这次对led彩屏的通讯开发就规定校验码高位在前低位在后，也就不需要转换高低位)
//        crc = ((crc & 0x0000FF00) >> 8) | ((crc & 0x000000FF) << 8);
//        return crc;
//    }
//
//    public static int crc16(byte[] bytes, int len) {
//        int crc;
//        int i, j;
//        crc = 0xffff;
//        for (i = 0; i < len; i++) {
//            crc = crc ^ (bytes[i] & 0xff);
//            for (j = 0; j < 8; j++) {
//                if ((crc & 0x0001) == 1) {
//                    crc = (crc >> 1) ^ 0xA001;
//                } else {
//                    crc = crc >> 1;
//                }
//            }
//        }
//        return crc;
//    }
}
