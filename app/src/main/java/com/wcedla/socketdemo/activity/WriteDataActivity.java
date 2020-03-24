package com.wcedla.socketdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class WriteDataActivity extends AppCompatActivity {

    /**
     * REBOOT
     */
    String rebootFrameContent = "26 01 00 00 00 00 00 00 00 00 00 00 10 09 00 01 01 06 54 4F 4F 42 45 52 1B 63";
    /**
     * 设置扫描频段
     * 400-3000
     * 3001-6000
     */
    String frequenceFrameContent = "26 01 00 00 00 00 00 00 00 00 00 00 10 69 00 10 80 66 02 00 00 00 90 01 00 00 00 00 00 00 b8 0b 00 00 00 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 b9 0b 00 00 00 00 00 00 70 17 00 00 00 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 8C C5";
    /**
     * 设置扫描模式
     * 01 扇区扫描 + 选中8个扫描单元
     */
    String scanModeFrameContent = "26 01 00 00 00 00 00 00 00 00 00 00 10 0C 00 14 80 09 01 01 01 01 01 01 01 01 01 9B CC";
    /**
     * 设置环境模式
     * 郊区模式
     */
    String environmentModeFrameContent = "26 01 00 00 00 00 00 00 00 00 00 00 10 04 00 15 80 01 01 95 FA";
    /**
     * 设置监测模式为图传
     */
    String imgTranslateFrameContent = "26 01 00 00 00 00 00 00 00 00 00 00 10 03 00 31 80 00 6D 9F";

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 2000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("SocketWriteThread");
            return thread;
        }
    });

    DatagramSocket datagramSocket;
    DatagramPacket receiverPacket;
    byte[] sendDataBuf;
    byte[] receiveBytes;

    EditText ipInput;
    EditText portInput;
    RadioButton rebootRadio;
    RadioButton frequencyRadio;
    RadioButton scanRadio;
    RadioButton environmentRadio;
    RadioButton imgRadio;
    Button writeSend;
    TextView receive;
    TextView writeClearReceive;
    TextView analysisData;

    String ip;
    int port;
    String sendString = rebootFrameContent;
    private String TAG = "wcedlaLog";
    private boolean receiveData = false;
    private volatile boolean stopReceive = false;
    private String receiveText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_data);
        initdata();
        initListener();
    }

    private void initdata() {
        ipInput = findViewById(R.id.writeIpInput);
        portInput = findViewById(R.id.writePortInput);
        rebootRadio = findViewById(R.id.rebootRadio);
        frequencyRadio = findViewById(R.id.frequencyRadio);
        scanRadio = findViewById(R.id.scanModeRadio);
        environmentRadio = findViewById(R.id.environmentRadio);
        imgRadio = findViewById(R.id.imgRadio);
        writeSend = findViewById(R.id.writeSend);
        receive = findViewById(R.id.writeReceiveContent);
        writeClearReceive = findViewById(R.id.writeClearReceive);
        analysisData = findViewById(R.id.writeAnalysisData);
        SystemUtils.setInputFilter(ipInput, "^[0-9.]+$", 15);
        SystemUtils.setInputFilter(portInput, "^[0-9]+$", 7);
        ipInput.setText(SocketData.IP);
        portInput.setText(SocketData.PORT + "");
    }

    private void initListener() {
        rebootRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChange(buttonView, isChecked);
                sendString = rebootFrameContent;
            }
        });
        frequencyRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChange(buttonView, isChecked);
                sendString = frequenceFrameContent;
            }
        });
        scanRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChange(buttonView, isChecked);
                sendString = scanModeFrameContent;
            }
        });
        environmentRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChange(buttonView, isChecked);
                sendString = environmentModeFrameContent;
            }
        });
        imgRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChange(buttonView, isChecked);
                sendString = imgTranslateFrameContent;
            }
        });
        writeSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sendString)) {
                    sendDataBuf = getHexData(sendString);
                    sendWriteData();
                } else {
                    Toast.makeText(WriteDataActivity.this, "请选择发送的数据!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        writeClearReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receive.setText("");
                analysisData.setVisibility(View.GONE);
            }
        });
        analysisData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeAnalysisIntent = new Intent(WriteDataActivity.this, WriteDataAnalysisActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("receiveText", receiveText);
                writeAnalysisIntent.putExtras(bundle);
                startActivity(writeAnalysisIntent);
            }
        });

    }

    private void checkChange(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            rebootRadio.setChecked(false);
            frequencyRadio.setChecked(false);
            scanRadio.setChecked(false);
            environmentRadio.setChecked(false);
            imgRadio.setChecked(false);
            buttonView.setChecked(true);
        }
    }

    private void sendWriteData() {
        ip = ipInput.getText().toString();
        String portString = portInput.getText().toString();
        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(portString)) {
            port = Integer.parseInt(portString);
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
                                Toast.makeText(WriteDataActivity.this, "发送成功!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WriteDataActivity.this, "发送失败!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    Thread nowThread = Thread.currentThread();
                    Log.d(TAG, "当前线程:" + nowThread.getName());
                    if (nowThread.getName().equals("SocketWriteThread")) {
                        nowThread.interrupt();
                    }
                    //开启线程接收数据
                    if (!receiveData) {
                        startReceiveData();
                    }
                }
            });
        }
    }

    private void startReceiveData() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                receiveData = true;
                while (!stopReceive) {
                    //接收数据,使用DatagramPacket，用来接收长度为 length 的数据包。
                    receiveBytes = new byte[2000];
                    receiverPacket = new DatagramPacket(receiveBytes, receiveBytes.length);
                    try {
                        datagramSocket.receive(receiverPacket);
                        Log.d(TAG, "接受到的数据(10进制转字符显示):" + new String(receiveBytes, Charset.forName("GB2312")));
                        receiveText = bytes2HexString(receiveBytes);
                        Log.d(TAG, "接收到的数据转十六进制显示:" + receiveText);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                receive.setText(receiveText);
                                analysisData.setVisibility(View.VISIBLE);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                receiveData = false;
            }
        });
    }

    private byte[] getHexData(String originData) {
        String[] commandArray = originData.split(" ");
        byte[] commandBytes = new byte[commandArray.length];
        int index = 0;
        for (String command : commandArray) {
            //将传输的16进制转为10进制传输，服务器收到数据进行转换
            commandBytes[index] = (byte) (Integer.parseInt(command, 16));
            index += 1;
        }
        for (byte b : commandBytes) {
            Log.d(TAG, "转换后的值(10进制表示):" + b);
        }
        return commandBytes;
    }

    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase() + " ";
        }

        return r;
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
}
