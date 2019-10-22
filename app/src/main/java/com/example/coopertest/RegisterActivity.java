package com.example.coopertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.litepal.LitePal;

public class RegisterActivity extends AppCompatActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private EditText pwdacknowledgeEdit;
    private EditText emailEdit;
    private EditText nicknameEdit;
    private EditText nameEdit;
    private EditText collegeEdit;
    private RadioGroup nRg;
    private RadioButton radioButton;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LitePal.getDatabase();

        accountEdit = (EditText) findViewById(R.id.account_reg);
        passwordEdit = (EditText) findViewById(R.id.password_reg);
        pwdacknowledgeEdit = (EditText) findViewById(R.id.pwdacknowledge_reg);
        emailEdit = (EditText) findViewById(R.id.email_reg);
        nicknameEdit = (EditText) findViewById(R.id.nickname_reg);
        nameEdit = (EditText) findViewById(R.id.name_reg);
        collegeEdit = (EditText) findViewById(R.id.college_reg);
        nRg = findViewById(R.id.rg_reg);
        register = (Button) findViewById(R.id.register);

        nRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = group.findViewById(checkedId);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String pwdacknowledge = pwdacknowledgeEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String nickname = nicknameEdit.getText().toString();
                String name = nameEdit.getText().toString();
                String college = collegeEdit.getText().toString();
                String identity = (String) radioButton.getText();

                if (!password.equals(pwdacknowledge)){
                    Toast.makeText(RegisterActivity.this,"两次输入的密码不一致!",Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User();
                    user.setTel(account);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.setNickname(nickname);
                    user.setName(name);
                    user.setCollege(college);
                    user.setIdentity(identity);
                    user.save();

                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
