package com.example.coopertest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CooperationActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    public LocationClient mLocationClient;

    private String type;
    private String sort;

    private String address;

    private Coop[] coops={
            new Coop("发布时间:"+"2019/4/1","组织名:"+"**基金会","联系人:"+"甲xx","地点:"+"成都市_温江区","赞助：提供校园公益赞助"),
            new Coop("发布时间:"+"2019/4/2","组织名:"+"**商家","联系人:"+"乙xx","地点:"+"成都市_温江区","赞助：提供校园小型活动赞助，求推广"),
            new Coop("发布时间:"+"2019/4/3","组织名:"+"**机构","联系人:"+"丙xx","地点:"+"成都市_温江区","赞助:各大小型校园班级活动"),
            new Coop("发布时间:"+"2019/4/4","组织名:"+"**组织","联系人:"+"丁xx","地点:"+"成都市_温江区","赞助:提供校园活动赞助，含资金和物资"),
            new Coop("发布时间:"+"2019/4/5","组织名:"+"**公司","联系人:"+"戊xx","地点:"+"成都市_温江区","赞助:赞助校园文体类活动宣传"),
            new Coop("发布时间:"+"2019/4/6","组织名:"+"**医院","联系人:"+"庚xx","地点:"+"成都市_温江区","合作:寻找高校红十字会组织无偿献血宣传"),
            new Coop("发布时间:"+"2019/4/7","组织名:"+"**中学","联系人:"+"辛xx","地点:"+"成都市_温江区","合作：寻找高校一同组织校园文化节活动"),
            new Coop("发布时间:"+"2019/4/8","组织名:"+"**社区","联系人:"+"xx","地点:"+"成都市_温江区","合作：寻找附近校园组织参与社区文化活动策划（有偿)"),
            new Coop("发布时间:"+"2019/4/9","组织名:"+"**社团","联系人:"+"xx","地点:"+"成都市_温江区","合作:寻找校内外组织共同举办xx活动"),
            new Coop("发布时间:"+"2019/4/10","组织名:"+"**大学","联系人:"+"xx","地点:"+"成都市_温江区","合作:寻求xx高校合作"),
    };

    private List<Coop> coopList = new ArrayList<>();
    private CoopAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(CooperationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(CooperationActivity.this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(CooperationActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(CooperationActivity.this,permissions,1);
        }
        else {
            requestLocation();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        initCoops(null,null);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CoopAdapter(coopList);
        recyclerView.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCoops();
            }
        });

    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }
                else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    address = bdLocation.getProvince()+"_"+bdLocation.getCity()+"_"+bdLocation.getDistrict();
                }
            });
        }

        public void onConnectHotSpotMessage(String s,int i){

        }

    }

    private void refreshCoops(){
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
                        initCoops(type,sort);
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initCoops(String type,String sort){
        coopList.clear();
        if ((type == null && sort == null) || (type == null && sort == "综合排序")){
            for (int i = 0;i<coops.length;i++){
                coopList.add(coops[i]);
            }
        }
        else {
            Coop temp;
            for (int i = 0;i<coops.length-1;i++){
                for (int j = i+1;j<coops.length;j++){
                    if (coops[i].getTime().compareTo(coops[j].getTime()) >= 0){
                        continue;
                    }
                    else {
                        temp = coops[j];
                        coops[j] = coops[i];
                        coops[i] = temp;
                    }
                }
            }
            temp = null;
            for (int i = 0;i<coops.length;i++){
                if (coops[i].getTitle().substring(0,2).equals(type)){
                    coopList.add(coops[i]);
                }
                else continue;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.choose:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("筛选条件");

                final View v = getLayoutInflater().inflate(R.layout.dialoglayout,null);
                builder.setView(v);

                RadioGroup Rg1 = v.findViewById(R.id.rg1);
                RadioGroup Rg2 = v.findViewById(R.id.rg2);

                Rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb1 = group.findViewById(checkedId);
                        type = (String) rb1.getText();
                    }
                });
                Rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb2 = group.findViewById(checkedId);
                        sort = (String) rb2.getText();
                    }
                });

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refreshCoops();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
            default:
                break;
        }
        return true;
    }
}
