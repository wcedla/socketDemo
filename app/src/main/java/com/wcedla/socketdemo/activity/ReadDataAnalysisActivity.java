package com.wcedla.socketdemo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wcedla.socketdemo.R;
import com.wcedla.socketdemo.data.ReadDataParameter;

import java.util.ArrayList;
import java.util.List;

public class ReadDataAnalysisActivity extends AppCompatActivity {

    private static final String TAG = "wcedlaLog";
    Bundle bundle;
    TextView splitTextTv;
    TextView deviceNumberTv;
    TextView fpgaVersionTv;
    TextView softwareVersionTv;
    TextView dateLengthTv;
    LinearLayout parameterRoot;
    TextView crcDataTv;

    String splitText;
    String receiveText = "";
    String deviceNumber = "";
    String fpgaVersion;
    String softwareVersion;
    String dataLength;
    int frameCount = 0;
    List<ReadDataParameter> readDataParameterList = new ArrayList<>();
    String crcData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data_analysis);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            receiveText = bundle.getString("receiveData");
        }
        initView();
        analysisData();
    }

    private void initView() {
        splitTextTv = findViewById(R.id.originTextContent);
        deviceNumberTv = findViewById(R.id.deviceNumberContent);
        fpgaVersionTv = findViewById(R.id.fpgaContent);
        softwareVersionTv = findViewById(R.id.softwareVersionContent);
        dateLengthTv = findViewById(R.id.dataLengthContent);
        parameterRoot = findViewById(R.id.parameterRoot);
        crcDataTv = findViewById(R.id.crcContent);
    }

    private void analysisData() {
        if (!TextUtils.isEmpty(receiveText)) {
            String[] receiveArray = receiveText.split(" ");
            if (receiveArray[0].equals("25")) {
                deviceNumber = receiveArray[8] + receiveArray[7] + receiveArray[6] + receiveArray[5] + receiveArray[4] + receiveArray[3] + receiveArray[2] + receiveArray[1];
                fpgaVersion = Integer.parseInt(receiveArray[9], 16) + "." + Integer.parseInt(receiveArray[10], 16);
                softwareVersion = Integer.parseInt(receiveArray[11], 16) + "." + Integer.parseInt(receiveArray[12], 16);
                if (receiveArray[16].equals("03")) {
                    int length = Integer.parseInt(receiveArray[18] + receiveArray[17], 16);
                    dataLength = String.valueOf(length);
                    frameCount = length + 21;
                    splitText = receiveText.substring(0, frameCount * 3 - 1);
                }
                int nowIndex = 19;
                while (nowIndex < frameCount - 2) {
                    int paraLength = Integer.parseInt(receiveArray[nowIndex + 2], 16);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = paraLength - 1; i >= 0; i--) {
                        stringBuilder.append(receiveArray[nowIndex + 3 + i]);
                        if (i != 0) {
                            stringBuilder.append(" ");
                        }
                    }
                    readDataParameterList.add(new ReadDataParameter(receiveArray[nowIndex + 1] + receiveArray[nowIndex], paraLength + "", stringBuilder.toString()));
                    nowIndex += paraLength + 3;
                }
                crcData = receiveArray[frameCount - 1] + " " + receiveArray[frameCount - 2];
            }
            Log.d(TAG, "数值查看:分隔数据源长度:" + frameCount + "分隔源数据:" + splitText + "设备编码:" + deviceNumber + ",fpga编码:" + fpgaVersion + ",软件版本:" + softwareVersion + ",数据长度:" + dataLength + ",参数个数:" + readDataParameterList.size() + ",CRC值:" + crcData);
            for (ReadDataParameter readDataParameter : readDataParameterList) {
                View view = LayoutInflater.from(this).inflate(R.layout.read_data_parameter_item, parameterRoot, false);
                TextView parameterCode = view.findViewById(R.id.parameterNoContent);
                TextView parameterLength = view.findViewById(R.id.parameterLengthContent);
                TextView parameterData = view.findViewById(R.id.parameterDataContent);
                parameterCode.setText(readDataParameter.getParameterCode());
                parameterLength.setText(readDataParameter.getParameterLength());
                parameterData.setText(readDataParameter.getParameterData());
                parameterRoot.addView(view);
                Log.d(TAG, "参数值查看:" + readDataParameter.getParameterCode() + "," + readDataParameter.getParameterLength() + "," + readDataParameter.getParameterData());
            }
            splitTextTv.setText(splitText);
            deviceNumberTv.setText(deviceNumber);
            fpgaVersionTv.setText(fpgaVersion);
            softwareVersionTv.setText(softwareVersion);
            dateLengthTv.setText(dataLength);
            crcDataTv.setText(crcData);

        }

    }
}
