package com.example.coopertest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TalkActivity extends AppCompatActivity {

    private List<Talk> talkList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;     //刷新
    TalkAdapter adapter;
    private Button btn_Home,btn_Coroperation,btn_Public,btn_Talk,btn_My;
    private Drawable drawable_Home,drawable_Coroperation,drawable_Public,drawable_Talk,drawable_My;
    private Talk[] talk={new Talk("xxxx比赛策划如何实现创新？","2019年04月24日 10：00","xx组织同学甲"),new Talk("社团活动集锦","2019年04月13日 09：00","xx社团"),
            new Talk("xxxx商店推出新品","2019年04月12日 08:00","xxx商家"),new Talk("xx考研机构x%成功上岸","2019年04月13日 07：00","xxx考研"),
            new Talk("xx校园活动圆满成功","2019年04月12日 06：00","xx校园组织")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        initTalk();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new TalkAdapter(talkList);
        recyclerView.setAdapter(adapter);
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refreshtalk);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTalk();
            }
        });

        btn_Home=(Button) findViewById(R.id.button_Home);
        btn_Coroperation=(Button) findViewById(R.id.button_Coroperation);
        btn_Public=(Button) findViewById(R.id.button_Public);
        btn_Talk=(Button) findViewById(R.id.button_Talk);
        btn_My=(Button) findViewById(R.id.button_Mine);
        drawable_Home=getResources().getDrawable(R.drawable.home1);            //底部按钮设置图片
        drawable_Home.setBounds(0,0,115,115);
        btn_Home.setCompoundDrawables(null,drawable_Home,null,null);
        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                              //点击主页按钮跳转
                Intent i= new Intent(TalkActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        drawable_Coroperation=getResources().getDrawable(R.drawable.shake1);
        drawable_Coroperation.setBounds(0,0,125,125);
        btn_Coroperation.setCompoundDrawables(null,drawable_Coroperation,null,null);
        btn_Coroperation.setOnClickListener(new View.OnClickListener() {           //点击合作按钮跳转
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TalkActivity.this,CooperationActivity.class);
                startActivity(intent);
            }
        });

        drawable_Public=getResources().getDrawable(R.drawable.ic_public2);
        drawable_Public.setBounds(0,0,135,135);
        btn_Public.setCompoundDrawables(null,drawable_Public,null,null);
        btn_Public.setOnClickListener(new View.OnClickListener() {               //点击发布按钮跳转
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TalkActivity.this,onActivity.class);
                startActivity(intent);
            }
        });

        drawable_Talk=getResources().getDrawable(R.drawable.ic_talk);
        drawable_Talk.setBounds(0,0,125,125);
        btn_Talk.setCompoundDrawables(null,drawable_Talk,null,null);

        drawable_My=getResources().getDrawable(R.drawable.ic_mine_out);  //对应mine和mine_out
        drawable_My.setBounds(0,0,120,120);
        btn_My.setCompoundDrawables(null,drawable_My,null,null);
        btn_My.setOnClickListener(new View.OnClickListener() {             //点击我的按钮跳转
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TalkActivity.this,SelfActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton f_button=(FloatingActionButton)findViewById(R.id.floatButton);            //悬浮按钮的监听器，不需要传值
        f_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TalkActivity.this,PublicContent.class);
                startActivity(intent);
            }
        });

    }
    private void initTalk(){
        for (int i=0;i<10;i++){
            Random random=new Random();
            int index=random.nextInt(talk.length);
            talkList.add(talk[index]);
        }
    }
    private void refreshTalk(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        talkList.clear();  //清除原有数据
                        Intent intent=getIntent();
                        String title=intent.getStringExtra("title");
                        String time=intent.getStringExtra("time");
                        String name=intent.getStringExtra("name");
                        talkList.add(new Talk(title,time,name));
                        initTalk();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}