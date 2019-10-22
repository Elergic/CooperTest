package com.example.coopertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class reportSuccessfullyActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_successfully);
        Button button1=(Button)findViewById(R.id.back2main);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back2main:
                Intent intent = new Intent(reportSuccessfullyActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
