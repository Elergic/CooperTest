package com.example.coopertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class PwdUpdateActivity extends AppCompatActivity {

    private EditText newpwdEdit;
    private Button submit;
    private String telphone;
    private String password;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwdupdate);

        Intent intent = getIntent();
        telphone = intent.getStringExtra("TEL");

        users = DataSupport.select("password").where("tel = ?",telphone).find(User.class);
        for (User user:users){
            password = user.getPassword();
        }

        newpwdEdit = (EditText) findViewById(R.id.new_pwd);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpwd = newpwdEdit.getText().toString();

                if (newpwd == null){
                    Toast.makeText(PwdUpdateActivity.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User();
                    user.setPassword(newpwd);
                    user.updateAll("tel = ? and password = ?",telphone,password);

                    Toast.makeText(PwdUpdateActivity.this,"密码修改成功!",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PwdUpdateActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
