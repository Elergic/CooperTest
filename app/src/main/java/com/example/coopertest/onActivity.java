package com.example.coopertest;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.bigkoo.pickerview.OptionsPickerView;
//import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
///import com.bigkoo.pickerview.listener.OnOptionsSelectListener;


public class onActivity extends AppCompatActivity implements View.OnClickListener{
    EditText mEditText,titleEditText,detailEditText,moneyEditText,thingEditText,numberEditText;
    TextView roption,actoption;
    private RadioGroup group_temo;
    private RadioButton checkRadioButton;
    String title,detail,thing,deadline,range,actype;
    int money,number ;
    String maintype;
    String name;
    OptionsPickerView pvOptions,actoptions;
    //  省份
    ArrayList<String> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

      /*  RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycle_view) ;
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        */
        //确认发布
        Button button=(Button)findViewById(R.id.submit);
        button.setOnClickListener((View.OnClickListener) this);
        Button back=(Button)findViewById(R.id.title_back);
        back.setOnClickListener((View.OnClickListener) this);
        //标题
        titleEditText = (EditText) findViewById(R.id.title);
        title= titleEditText.getText().toString();
        //联系人姓名
        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        //描述
        detailEditText = (EditText) findViewById(R.id.detail);
        detail= detailEditText.getText().toString();
        //资金
        moneyEditText = (EditText) findViewById(R.id.money);


        //物资
        thingEditText = (EditText) findViewById(R.id.thing);

        //申请数
        numberEditText = (EditText) findViewById(R.id.number);

        //截止时间
        mEditText = (EditText) findViewById(R.id.deadline);
        mEditText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlog();
                    return true;
                }
                return false;
            }
        });
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlog();
                    deadline= mEditText.getText().toString();
                }
            }
        });
        //选择范围
        roption = (TextView) findViewById(R.id.roption);
        //    按钮点击事件
        roption.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showPickerView();
                    //   range= rangeEditText.getText().toString();
                    range= roption.getText().toString();
                    return true;
                }
                return false;
            }
        });
        roption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showPickerView();
                    // range= rangeEditText.getText().toString();
                    range= roption.getText().toString();
                }
            }
        });
        //选择活动类别
        actoption = (TextView) findViewById(R.id.activitytype);
        //    按钮点击事件
        actoption.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showPickerView1();
                    //   range= rangeEditText.getText().toString();
                    actype= actoption.getText().toString();
                    return true;
                }
                return false;
            }
        });
        actoption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showPickerView1();
                    // range= rangeEditText.getText().toString();
                    actype= actoption.getText().toString();
                }
            }
        });
        group_temo = (RadioGroup)findViewById(R.id.radioGroup1);
        //改变默认的选项
        group_temo.check(R.id.spo);
        //获取默认被被选中值
        checkRadioButton = (RadioButton) group_temo.findViewById(group_temo.getCheckedRadioButtonId());
        group_temo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //点击事件获取的选择对象
                checkRadioButton = (RadioButton) group_temo.findViewById(checkedId);
                //maintype存入数据库
                //   Toast.makeText(getApplicationContext(), "您选择的类别是"+checkRadioButton.getText(), Toast.LENGTH_LONG).show();
            }
        });
        maintype= (String) checkRadioButton.getText();
    }
    //确认发布
    public void onClick(View v){
        switch(v.getId()){
            case R.id.submit:
                int tag=1;
                if(moneyEditText.getText()==null||moneyEditText.getText().toString().equals("") ||moneyEditText.getText().toString().equals("null"))
                    money=0;
                else if((moneyEditText.getText() != null) && !moneyEditText.getText().toString().equals("") && !moneyEditText.getText().toString().equals("null")&&isNumeric(moneyEditText.getText().toString())==true)
                    money=Integer.valueOf(moneyEditText.getText().toString());
                else
                    Toast.makeText(getApplicationContext(), "请输入正确的资金金额", Toast.LENGTH_LONG).show();

                if(thingEditText.getText()==null||thingEditText.getText().toString().equals("") ||thingEditText.getText().toString().equals("null"))
                    thing="无";
                else
                    thing= thingEditText.getText().toString();

                if ((numberEditText.getText() != null)&& !numberEditText.getText().toString().equals("0") && !numberEditText.getText().toString().equals("") && !numberEditText.getText().toString().equals("null")&&(isNumeric(numberEditText.getText().toString()) == true))
                    number = Integer.valueOf(numberEditText.getText().toString());
                else {
                    Toast.makeText(getApplicationContext(), "请正确填写允许的申请数", Toast.LENGTH_LONG).show();
                    tag=0;
                }
               /* if(maintype.equals("赞助"))
                {
                    if (money==0&&thing.equals("无"));
                        Toast.makeText(getApplicationContext(), "您选择的是赞助，请输入你的（需求/提供）的金额或物资", Toast.LENGTH_LONG).show();
                        tag=0;
                }*/
                //title 标题 //detail 描述  maintype 申请赞助/合作  money 资金 thing 物资  range 范围 deadline 截止时间 number(int) 申请数  actype活动类型
                //组织，发布时间（系统时间）获取
                //加入数据库
                //跳转到成功界面reportSuccessfully
                //  Toast.makeText(getApplicationContext(), "您选择的类别是"+range+number+money, Toast.LENGTH_LONG).show();
                if (tag==1) {

                    Calendar calendar = Calendar.getInstance();
                    String year = String.valueOf(calendar.get(Calendar.YEAR));
                    String month = String.valueOf(calendar.get(Calendar.MONTH));
                    String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                    String time = year + "/" + month + "/" + day;

                    Publication publication = new Publication();
                    publication.setTime(time);
                    publication.setName(name);
                    publication.setTitle(title);
                    publication.setDetail(detail);
                    publication.setMaintype(maintype);
                    publication.setMoney(money);
                    publication.setThing(thing);
                    publication.setRange(range);
                    publication.setDeadline(deadline);
                    publication.setNumber(number);
                    publication.setActype(actype);
                    publication.setOrganization("西南财经大学青年志愿者协会");
                    publication.save();

                    Intent intent = new Intent(onActivity.this, reportSuccessfullyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.title_back:
                //跳转到上一页
                finish();
            default:
                break;
        }
    }
    //截止时间
    protected void showDatePickDlog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(onActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                onActivity.this.mEditText.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    private void showPickerView() {
        final List<String> listData = getData();
        //  获取json数据
        String province_data_json = JsonFileReader.getJson(this, "province.json");
        //  解析json数据
        parseJson(province_data_json);
        //  设置选择的三级单位
        // pvOptions.setLabels("省", "市", "区");
        // pvOptions.setTitle("选择城市");

        //  设置是否循环滚动
        //pvOptions.setCyclic(false, false, false);
        // 设置默认选中的三级项目


//      监听选中
        pvOptions = new OptionsPickerBuilder(onActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String city = provinceBeanList.get(options1);
                String address;
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1)
                            + " " + districtList.get(options1).get(option2).get(options3);
                } else {
                    address = provinceBeanList.get(options1)
                            + "_" + cityList.get(options1).get(option2)
                            + "_" + districtList.get(options1).get(option2).get(options3);
                }
                roption.setText(address);

            }})
                .setSelectOptions(0,0,0)//设置选择第一个
                .setOutSideCancelable(false)//点击背的地方不消失
                .build();//创建
//      把数据绑定到控件上面
        pvOptions.setPicker(provinceBeanList, cityList, districtList);
        // pvOptions.setPicker(listData);
//      展示
        pvOptions.show();
    }
    //  解析json填充集合
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.optString("name");
                provinceBeanList.add(provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    private void showPickerView1() {
        final List<String> listData = getData();

//      监听选中
        actoptions = new OptionsPickerBuilder(onActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//               返回的分别是三个级别的选中位置
//              展示选中数据
                actoption.setText(listData.get(options1));
            }
        })
                .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(false)//点击背的地方不消失
                .build();//创建
//      把数据绑定到控件上面
        actoptions.setPicker(listData);
//      展示
        actoptions.show();
    }

    private List<String> getData() {
        List<String> activitytype = new ArrayList<>();
        activitytype.add("不限");
        activitytype.add("文体娱乐");
        activitytype.add("科技赛事");
        activitytype.add("公益志愿");
        activitytype.add("社会实践");

        return activitytype;

    }

}