package com.example.coopertest;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CoopActivity extends AppCompatActivity {

    public static final String COOP_TITLE = "coop_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coop);

        Intent intent = getIntent();
        String coopTitle = intent.getStringExtra(COOP_TITLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        TextView coopContentText = (TextView) findViewById(R.id.coop_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(coopTitle);
        String coopContent = generateCoopContent(coopTitle);
        coopContentText.setText(coopContent);

    }

    private String generateCoopContent(String coopTitle) {
        StringBuilder coopContent = new StringBuilder();
        coopContent.append("我的标题是:");
        coopContent.append(coopTitle);
        return coopContent.toString();
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
