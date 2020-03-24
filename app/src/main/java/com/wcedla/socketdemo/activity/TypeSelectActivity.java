package com.wcedla.socketdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.wcedla.socketdemo.R;

public class TypeSelectActivity extends AppCompatActivity {

    Button readData;
    Button writeData;
    Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_select);
        readData=findViewById(R.id.readData);
        writeData=findViewById(R.id.writeData);
        test=findViewById(R.id.test);
        readData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readDataIntent=new Intent(TypeSelectActivity.this,MainActivity.class);
                startActivity(readDataIntent);
            }
        });
        writeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeDataIntent=new Intent(TypeSelectActivity.this,WriteDataActivity.class);
                startActivity(writeDataIntent);
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent demoIntent=new Intent(TypeSelectActivity.this,DemoActivity.class);
                startActivity(demoIntent);
            }
        });

    }
}
