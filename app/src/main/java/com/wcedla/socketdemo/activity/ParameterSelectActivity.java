package com.wcedla.socketdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wcedla.socketdemo.R;
import com.wcedla.socketdemo.adapter.ParameterAdapter;
import com.wcedla.socketdemo.data.ParameterData;

import java.util.ArrayList;
import java.util.List;

public class ParameterSelectActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button confirmButton;
    List<ParameterData> dataList = new ArrayList<>();
    List<Integer> selectList = new ArrayList<>();
    String headData="";
    String frameContent="";
    String frameData="";
    String sendData="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_select);
        recyclerView = findViewById(R.id.recyclerView);
        confirmButton = findViewById(R.id.confirmPara);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateParameterData();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        initParameterData();
        ParameterAdapter parameterAdapter = new ParameterAdapter(this, dataList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(parameterAdapter);

    }

    private void initParameterData() {
        headData = "26 01 00 00 00 00 00 00 00 00 00 ";
        dataList.add(new ParameterData("设备编号", "1", 8));
        dataList.add(new ParameterData("FPGA版本号", "2", 2));
        dataList.add(new ParameterData("ARM版本号", "3", 2));
        dataList.add(new ParameterData("环境温度", "4", 4));
        dataList.add(new ParameterData("CPU使用率", "5", 4));
        dataList.add(new ParameterData("FPGA发射通道使用率", "6", 4));
        dataList.add(new ParameterData("定位位置", "3A", 20));
        dataList.add(new ParameterData("授时时间", "3B", 8));
        dataList.add(new ParameterData("状态上报间隔", "3C", 1));
        dataList.add(new ParameterData("定位速度", "3D", 12));
        dataList.add(new ParameterData("授时同步状态", "201", 4));
        dataList.add(new ParameterData("晶振工作状态", "220", 4));
        dataList.add(new ParameterData("读取GPS伪卫星状态", "2036", 4));
        dataList.add(new ParameterData("读取GLONASS伪卫星状态", "2037", 4));
        dataList.add(new ParameterData("系统状态信息","2038",9999999));
        dataList.add(new ParameterData("获取探测设备状态", "8002", 2));
        dataList.add(new ParameterData("获取GPS数据", "8004", 46));
        dataList.add(new ParameterData("频谱结果数据", "8011", 153));
        dataList.add(new ParameterData("测向结果数据", "8042", 40));
        dataList.add(new ParameterData("测向信号消失", "8044", 8));
        dataList.add(new ParameterData("初始化状态", "8060", 2));
    }

    private void calculateParameterData() {
        if (recyclerView.getAdapter() != null) {
            selectList.clear();
            selectList.addAll(((ParameterAdapter) recyclerView.getAdapter()).getSelect());
            if(!selectList.isEmpty()) {
                getParameterContent();
                String frameContentData = "00 03";
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(frameContentData);
                stringBuilder.append(" ");
                stringBuilder.append(getParameterLength());
                stringBuilder.append(" ");
                stringBuilder.append(frameContent);
                stringBuilder.append(getParameterCrc(stringBuilder.toString()).toUpperCase());
                Log.d("wcedlaLog", "帧体数据: " + stringBuilder.toString());
                frameData = stringBuilder.toString();
                sendData = headData + frameData;
                Log.d("wcedlaLog", "发送帧数据:" + sendData);
                Intent data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("sendData", sendData);
                data.putExtras(bundle);
                setResult(RESULT_OK, data);
                finish();
            }else {
                Toast.makeText(ParameterSelectActivity.this,"还未选择参数！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getParameterLength() {
        int length = selectList.size()*2;
        String lengthString=Integer.toHexString(length);
        lengthString=changeToSmallHex(lengthString);
        Log.d("wcedlaLog", "长度: "+lengthString);
        return lengthString;

    }

    private void getParameterContent() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : selectList) {
            String code=changeToSmallHex(dataList.get(i).getParameterCode());
            stringBuilder.append(code);
            stringBuilder.append(" ");
        }
        frameContent=stringBuilder.toString();
        Log.d("wcedlaLog", "帧体参数: " + frameContent);
    }

    private String getParameterCrc(String frameString)
    {
        String[] contentArray=frameString.split(" ");
        byte[] bytes=new byte[contentArray.length];
        int index=0;
        for(String content:contentArray){
            bytes[index]=(byte)Integer.parseInt(content,16);
            index+=1;
        }
        int crc=getCrc16(bytes,false);
        String crcString=changeToSmallHex(Integer.toHexString(crc));
        Log.d("wcedlaLog", "CRC校验值:"+crcString);
        return crcString;
    }

    private String changeToSmallHex(String originData){
        String changeString="";
        switch (originData.length()){
            case 1:
                changeString= "0"+originData+" 00";
                break;
            case 2:
                changeString=originData+" 00";
                break;
            case 3:
                changeString=originData.substring(1,3)+" 0"+originData.charAt(0);
                break;
            case 4:
                changeString=originData.substring(2,4)+" "+originData.substring(0,2);
                break;
                default:
                    break;
        }
        return changeString;
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

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase()+" ";
        }

        return r;
    }

}
