package com.wcedla.socketdemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import com.wcedla.socketdemo.R;
import com.wcedla.socketdemo.data.AnalysisReadResponseData;
import com.wcedla.socketdemo.data.ReadUdpData;
import com.wcedla.socketdemo.data.SpectrumData;
import com.wcedla.socketdemo.data.WriteUdpData;
import com.wcedla.socketdemo.utils.ParseUtil;
import com.wcedla.socketdemo.utils.UdpUtils;

import java.util.ArrayList;
import java.util.List;

import static com.wcedla.socketdemo.data.SocketData.CYCLE_SCAN;
import static com.wcedla.socketdemo.data.SocketData.EIGHTH_ANTENNA;
import static com.wcedla.socketdemo.data.SocketData.FIFTH_ANTENNA;
import static com.wcedla.socketdemo.data.SocketData.FIRST_ANTENNA;
import static com.wcedla.socketdemo.data.SocketData.READ_ARM_VERSION;
import static com.wcedla.socketdemo.data.SocketData.READ_DEVICE_NUMBER;
import static com.wcedla.socketdemo.data.SocketData.READ_FPGA_VERSION;
import static com.wcedla.socketdemo.data.SocketData.SUBURBAN_MODE;
import static com.wcedla.socketdemo.data.SocketData.THIRD_ANTENNA;

public class DemoActivity extends AppCompatActivity implements UdpUtils.UdpStatusListener {

    private static final String TAG = "wcedlaLog";
    Button sendData;
    TextView receiveData;
    Button stopReceive;
    Button test;
    Button test2;
    Button test3;
    Button test4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        sendData = findViewById(R.id.sendData);
        receiveData = findViewById(R.id.receiveData);
        stopReceive = findViewById(R.id.stopReceive);
        test = findViewById(R.id.testButton);
        test2 = findViewById(R.id.test2);
        test3=findViewById(R.id.test3);
        test4=findViewById(R.id.test4);
        UdpUtils.getInstance().initUdpSocket(DemoActivity.this);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpUtils.getInstance().initUdpSocket(DemoActivity.this);
                UdpUtils.getInstance().sendData(getHexData("26 01 00 00 00 00 00 00 00 00 00 00 03 1e 00 01 00 02 00 03 00 04 00 05 00 06 00 3A 00 3B 00 3C 00 3D 00 01 02 20 02 36 20 37 20 38 20 C5 B8"));
            }
        });
        stopReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpUtils.getInstance().destroyUdp();
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadUdpData.getInstance("01 02 03 04 05 06 07 08").getReadData(READ_DEVICE_NUMBER, READ_FPGA_VERSION, READ_ARM_VERSION);
            }
        });
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SpectrumData> spectrumDataList = new ArrayList<>();
                spectrumDataList.add(new SpectrumData(400000000L, 3000000000L, 1));
                spectrumDataList.add(new SpectrumData(3001000000L, 6000000000L, 1));
                WriteUdpData.getInstance("01 02 03 04 05 06 07 08").setSpectrumData(spectrumDataList);
                WriteUdpData.getInstance("01 02 03 04 05 06 07 08").setScanMode(CYCLE_SCAN, FIRST_ANTENNA, THIRD_ANTENNA, FIFTH_ANTENNA, EIGHTH_ANTENNA);
                WriteUdpData.getInstance("01 02 03 04 05 06 07 08").setEnvironmentMode(SUBURBAN_MODE);
                WriteUdpData.getInstance("01 02 03 04 05 06 07 08").setAttenuation(25.366);
                WriteUdpData.getInstance("01 02 03 04 05 06 07 08").setSingleDirectionFrequency(400000000L, 1000000);
            }
        });
        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        test4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    @Override
    public void sendSucceed() {
        Log.d(TAG, "发送成功啦！");
    }

    @Override
    public void sendFailed() {
        Log.e(TAG, "发送失败了！");
    }

    @Override
    public void receivedMessage(byte[] receiveBytes) {

        receiveBytes= ParseUtil.subByte(receiveBytes,8,receiveBytes.length-8);
        Logger.d("收到消息了");
        Logger.d(receiveBytes);
        AnalysisReadResponseData.getInstance(receiveBytes).analysis();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                receiveData.setText(bytes2HexString(receiveBytes));
//            }
//        });

    }

    @Override
    public void receivedMessageFailed() {
        Log.e(TAG, "消息接收失败！");
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
    protected void onDestroy() {
        super.onDestroy();
        UdpUtils.getInstance().destroyUdp();
    }
}
