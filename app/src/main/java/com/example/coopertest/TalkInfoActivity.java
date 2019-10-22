package com.example.coopertest;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class TalkInfoActivity extends AppCompatActivity {

    public static final String TALK_TITLE = "talk_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_info);

        Intent intent = getIntent();
        String talkTitle = intent.getStringExtra(TALK_TITLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        TextView coopContentText = (TextView) findViewById(R.id.talk_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(talkTitle);
        String coopContent = generateTalkContent(talkTitle);
        coopContentText.setText(coopContent);
    }

    private String generateTalkContent(String talkTitle) {
        StringBuilder talkContent = new StringBuilder();
        talkContent.append("我的标题是:");
        talkContent.append(talkTitle);
        return talkContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
