package com.example.coopertest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SelfActivity extends AppCompatActivity {

    private TextView telText;
    private TextView emailText;
    private TextView nameText;
    private TextView nicknameText;
    private TextView collegeText;
    private TextView identityText;
    private Button pwd_update;
    private Button inform_update;
    private Button history;
    private Button collection;
    private Button aboutus;
    private List<User> users;
    private String telphone;
    private Drawable drawable_pwd,drawable_inform,drawable_history,drawable_collection,drawable_aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);

        Intent intent = getIntent();
        telphone = intent.getStringExtra("TEL");

        telText = (TextView) findViewById(R.id.tel_text);
        emailText = (TextView) findViewById(R.id.email_text);
        nameText = (TextView) findViewById(R.id.name_text);
        nicknameText = (TextView) findViewById(R.id.nickname_text);
        collegeText = (TextView) findViewById(R.id.college_text);
        identityText = (TextView) findViewById(R.id.identity_text);
        pwd_update = (Button) findViewById(R.id.pwd_update);
        inform_update = (Button) findViewById(R.id.inform_update);
        history=(Button) findViewById(R.id.history);
        collection=(Button) findViewById(R.id.collection);
        aboutus=(Button) findViewById(R.id.aboutus);

        drawable_pwd=getResources().getDrawable(R.drawable.ic_pwd);        //底部按钮设置图片
        drawable_pwd.setBounds(0,0,120,120);
        pwd_update.setCompoundDrawables(null,drawable_pwd,null,null);

        drawable_inform=getResources().getDrawable(R.drawable.ic_inform);        //底部按钮设置图片
        drawable_inform.setBounds(0,0,120,120);
        inform_update.setCompoundDrawables(null,drawable_inform,null,null);

        drawable_history=getResources().getDrawable(R.drawable.ic_history);        //底部按钮设置图片
        drawable_history.setBounds(0,0,120,120);
        history.setCompoundDrawables(null,drawable_history,null,null);

        drawable_collection=getResources().getDrawable(R.drawable.ic_collection);        //底部按钮设置图片
        drawable_collection.setBounds(0,0,120,120);
        collection.setCompoundDrawables(null,drawable_collection,null,null);

        drawable_aboutus=getResources().getDrawable(R.drawable.ic_aboutus);        //底部按钮设置图片
        drawable_aboutus.setBounds(0,0,120,120);
        aboutus.setCompoundDrawables(null,drawable_aboutus,null,null);

        users = DataSupport.select("tel","email","name","nickname","college","identity").where("tel = ?",telphone).find(User.class);

        for (User user:users){
            telText.setText("用户名:"+user.getTel());
            telText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            emailText.setText("邮箱:"+user.getEmail());
            emailText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            nameText.setText("真实姓名:"+user.getName());
            nameText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            nicknameText.setText("昵称:"+user.getNickname());
            nicknameText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            collegeText.setText("学校:"+user.getCollege());
            collegeText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            identityText.setText("身份:"+user.getIdentity());
            identityText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
        }

        pwd_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfActivity.this,PwdUpdateActivity.class);
                for (User user:users){
                    intent.putExtra("TEL",user.getTel());
                }
                startActivity(intent);
            }
        });

        inform_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfActivity.this,InformActivity.class);
                for (User user:users){
                    intent.putExtra("TEL",user.getTel());
                }
                startActivity(intent);
            }
        });

    }
}
