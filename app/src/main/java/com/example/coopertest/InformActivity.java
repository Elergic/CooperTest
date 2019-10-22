package com.example.coopertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InformActivity extends AppCompatActivity {

    private EditText emailEdit;
    private EditText nicknameEdit;
    private Button update_submit;
    private String telphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);

        Intent intent = getIntent();
        telphone = intent.getStringExtra("TEL");

        emailEdit = (EditText) findViewById(R.id.email_update);
        nicknameEdit = (EditText) findViewById(R.id.nickname_update);
        update_submit = (Button) findViewById(R.id.update_submit);

        update_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String nickname = nicknameEdit.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setNickname(nickname);
                user.updateAll("tel = ?",telphone);

                Toast.makeText(InformActivity.this,"信息修改成功",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(InformActivity.this,SelfActivity.class);
                startActivity(intent);
            }
        });

    }
}
