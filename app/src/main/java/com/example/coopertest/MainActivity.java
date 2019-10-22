package com.example.coopertest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    private int imageIds[] = {   //滚动的图片
            R.drawable.news01_pic,
            R.drawable.news02_pic,
            R.drawable.news03_pic,
            R.drawable.news04_pic,
            R.drawable.news05_pic
    };
    private ArrayList<ImageView> images = new ArrayList<>();    //滚动视图列表
    private ViewPager vp;
    private int oldPosition = 0;//记录上一次点的位置
    private int currentItem; //当前页面
    private ScheduledExecutorService scheduledExecutorService;    //图片标题
    private String titles[] = new String[]{"图片1", "图片2", "图片3", "图片4", "图片5"};
    private ArrayList<View> dots = new ArrayList<View>();
    private TextView title;       //图片标题
    private ImageView imageview;   //滚动视图
    private TextView forecast;    //提示框
    private SwipeRefreshLayout swipeRefresh;

    private String account;
    private String password;
    private List<User> users;

    private Button btn_Home,btn_Coroperation,btn_Public,btn_Talk,btn_My;
    private Drawable drawable_Home,drawable_Coroperation,drawable_Public,drawable_Talk,drawable_My;

    private Needs[] needs={new Needs("赞助：提供校园公益赞助","发布时间:"+"2019/4/1","地点:"+"成都市_温江区","组织名:"+"**基金会","联系人:"+"甲xx"),new Needs("赞助：提供校园小型活动赞助，求推广","发布时间:"+"2019/4/2","成都市_温江区","组织名:"+"**商家","联系人:"+"乙xx"),
            new Needs("赞助:各大小型校园班级活动","发布时间:"+"2019/4/3","地点:"+"成都市_温江区","组织名:"+"**机构","联系人:"+"丙xx"),new Needs("赞助:提供校园活动赞助，含资金和物资","发布时间:"+"2019/4/4","地点:"+"成都市_温江区","组织名:"+"**组织","联系人:"+"丁xx"),
            new Needs("赞助:赞助校园文体类活动宣传","发布时间:"+"2019/4/5","地点:"+"成都市_温江区","组织名:"+"**公司","联系人:"+"戊xx"),new Needs("合作:寻找高校红十字会组织无偿献血宣传","发布时间:"+"2019/4/6","地点:"+"成都市_温江区","组织名:"+"**医院","联系人:"+"庚xx"),
            new Needs("合作：寻找高校一同组织校园文化节活动","发布时间:"+"2019/4/7","地点:"+"成都市_温江区","组织名:"+"**中学","联系人:"+"辛xx"),new Needs("合作：寻找附近校园组织参与社区文化活动策划（有偿)","发布时间:"+"2019/4/8","地点:"+"成都市_温江区","组织名:"+"**社区","联系人:"+"xx"),
            new Needs("合作:寻找校内外组织共同举办xx活动","发布时间:"+"2019/4/9","地点:"+"成都市_温江区","组织名:"+"**社团","联系人:"+"xx"),new Needs("合作:寻求xx高校合作","发布时间:"+"2019/4/10","地点:"+"成都市_温江区","组织名:"+"**大学","联系人:"+"xx"),
    };
    private List<Needs> needsList=new ArrayList<>();
    private NeedsAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        password = intent.getStringExtra("password");
        users = DataSupport.select("tel","password","nickname","name").where("tel = ? and password = ?",account,password).find(User.class);

        Slide();     //实现滑动功能的函数
        ViewFlipper vf=(ViewFlipper)findViewById(R.id.scroll_view);   //滚动新闻
        final TextView forecast=(TextView)findViewById(R.id.forecast) ;
        vf.addView(View.inflate(this,R.layout.noticelayout,null));
        vf.addView(View.inflate(this,R.layout.noticelayout,null));
        vf.addView(View.inflate(this,R.layout.noticelayout,null));

        btn_Home=(Button) findViewById(R.id.button_Home);
        btn_Coroperation=(Button) findViewById(R.id.button_Coroperation);
        btn_Public=(Button) findViewById(R.id.button_Public);
        btn_Talk=(Button) findViewById(R.id.button_Talk);
        btn_My=(Button) findViewById(R.id.button_Mine);

        drawable_Home=getResources().getDrawable(R.drawable.home2);        //底部按钮设置图片
        drawable_Home.setBounds(0,0,120,120);
        btn_Home.setCompoundDrawables(null,drawable_Home,null,null);

        drawable_Coroperation=getResources().getDrawable(R.drawable.shake1);
        drawable_Coroperation.setBounds(0,0,125,125);
        btn_Coroperation.setCompoundDrawables(null,drawable_Coroperation,null,null);
        btn_Coroperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CooperationActivity.class);
                LitePal.getDatabase();
                startActivity(intent);
            }
        });

        drawable_Public=getResources().getDrawable(R.drawable.ic_public2);
        drawable_Public.setBounds(0,0,135,135);
        btn_Public.setCompoundDrawables(null,drawable_Public,null,null);
        btn_Public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,onActivity.class);
                for (User user:users){
                    intent.putExtra("NAME",user.getName());
                }
                startActivity(intent);
            }
        });

        drawable_Talk=getResources().getDrawable(R.drawable.ic_talk1);
        drawable_Talk.setBounds(0,0,125,125);
        btn_Talk.setCompoundDrawables(null,drawable_Talk,null,null);
        btn_Talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                              //点击论坛按钮跳转
                Intent i= new Intent(MainActivity.this,TalkActivity.class);
                startActivity(i);
            }
        });

        drawable_My=getResources().getDrawable(R.drawable.ic_mine_out);  //对应mine和mine_out
        drawable_My.setBounds(0,0,120,120);
        btn_My.setCompoundDrawables(null,drawable_My,null,null);
        btn_My.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SelfActivity.class);
                for (User user:users){
                    intent.putExtra("TEL",user.getTel());
                }
                startActivity(intent);
            }
        });

        initNeeds();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager;
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new NeedsAdapter(needsList);
        recyclerView.setAdapter(adapter);

        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNeeds();
            }
        });
    }
    private void initNeeds(){
        needsList.clear();
        for (int i=0;i<50;i++){
            Random random=new Random();
            int index=random.nextInt(needs.length);
            needsList.add(needs[index]);
        }
    }

    private void refreshNeeds(){
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
                        initNeeds();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    public void Slide(){
        for (int i = 0; i < imageIds.length; i++) {
            imageview = new ImageView(this);
            imageview.setImageResource(imageIds[i]);
            imageview.setAdjustViewBounds(true);
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);//使图片充满控件大小
            images.add(imageview);
        }
        dots.add(findViewById(R.id.dot_0));  //显示的点 加入集合
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        title = (TextView) findViewById(R.id.tv_test_title);//获取图片标题的id
        vp = (ViewPager) findViewById(R.id.vp);//获取ViewPager 的id
        vp.setAdapter(new ViewPagerAdapter());
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                //设置页面刷新后的图片标题
                title.setText(titles[position]);
                oldPosition = position;
                currentItem = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    class ViewPagerAdapter extends PagerAdapter {
        public int getCount() {
            return images.size();
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //将视图移出视图组
            View v = images.get(position);
            container.removeView(v);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //将视图添加进视图组
            View v = images.get(position);
            container.addView(v);
            return v;
        }
    }

    protected void onStart(){
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //每隔四秒换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(),4,4, TimeUnit.SECONDS);//TimeUnit.SECONDS);
    }    //实现一个碎片的接口

    class ViewPagerTask implements Runnable{
        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            //更新界面
            handler.obtainMessage().sendToTarget();
        }
    }
    //在handler进行碎片跳转
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            vp.setCurrentItem(currentItem);
        }
    };


}