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
import com.wcedla.socketdemo.data.WriteDataParameter;

import java.util.ArrayList;
import java.util.List;

public class WriteDataAnalysisActivity extends AppCompatActivity {

    private static final String TAG = "wcedlaLog";
    Bundle bundle;
    String receiveText;
    TextView splitText;
    TextView deviceNumbr;
    TextView fpgaText;
    TextView softwareVersionText;
    TextView dataLengthText;
    LinearLayout addRoot;
    TextView crcRoot;
    TextView writeError;

    String splitString;
    String deviceNumberString;
    String fpgaString;
    String softwareString;
    String dataLengthString;
    String crcString;
    private int frameCount;

    List<WriteDataParameter> writeDataParameterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_data_analysis);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            receiveText = bundle.getString("receiveText");
        }
        initView();
        initData();
    }

    private void initView() {
        splitText = findViewById(R.id.writeOriginTextContent);
        deviceNumbr = findViewById(R.id.writeDeviceNumberContent);
        fpgaText = findViewById(R.id.writeFpgaContent);
        softwareVersionText = findViewById(R.id.writeSoftwareVersionContent);
        dataLengthText = findViewById(R.id.writeDataLengthContent);
        addRoot = findViewById(R.id.writeParameterRoot);
        crcRoot = findViewById(R.id.writeCrcContent);
        writeError = findViewById(R.id.writeError);
    }

    private void initData() {
        if (!TextUtils.isEmpty(receiveText)) {
            String[] receiveArray = receiveText.split(" ");
            if (receiveArray[0].equals("25")) {
                deviceNumberString = receiveArray[8] + receiveArray[7] + receiveArray[6] + receiveArray[5] + receiveArray[4] + receiveArray[3] + receiveArray[2] + receiveArray[1];
                fpgaString = Integer.parseInt(receiveArray[9], 16) + "." + Integer.parseInt(receiveArray[10], 16);
                softwareString = Integer.parseInt(receiveArray[11], 16) + "." + Integer.parseInt(receiveArray[12], 16);
                if (receiveArray[16].equals("10") || receiveArray[16].equals("90")) {
                    int length = Integer.parseInt(receiveArray[18] + receiveArray[17], 16);
                    dataLengthString = String.valueOf(length);
                    frameCount = length + 21;
                    splitString = receiveText.substring(0, frameCount * 3 - 1);
                    int nowIndex = 19;
                    for (int i = 0; i < length / 2; i++) {
                        writeDataParameterList.add(new WriteDataParameter(receiveArray[nowIndex + 1] + " " + receiveArray[nowIndex]));
                        nowIndex += 2;
                    }
                    crcString = receiveArray[frameCount - 1] + " " + receiveArray[frameCount - 2];
                }
                if (receiveArray[16].equals("90")) {
                    writeError.setVisibility(View.VISIBLE);
                }
                Log.d(TAG, "数值查看:分隔数据源长度:" + frameCount + "分隔源数据:" + splitString + "设备编码:" + deviceNumberString + ",fpga编码:" + fpgaString + ",软件版本:" + softwareString + ",数据长度:" + dataLengthString + ",参数个数:" + writeDataParameterList.size() + ",CRC值:" + crcString);
                for (WriteDataParameter writeDataParameter : writeDataParameterList) {
                    View view = LayoutInflater.from(this).inflate(R.layout.write_data_parameter_item, addRoot, false);
                    TextView parameterCode = view.findViewById(R.id.writeParameterCodeContent);
                    parameterCode.setText(writeDataParameter.getParameterCode());
                    addRoot.addView(view);
                    Log.d(TAG, "参数值查看:" + writeDataParameter.getParameterCode());
                }
                splitText.setText(splitString);
                deviceNumbr.setText(deviceNumberString);
                fpgaText.setText(fpgaString);
                softwareVersionText.setText(softwareString);
                dataLengthText.setText(dataLengthString);
                crcRoot.setText(crcString);
            }
        }
    }
}
