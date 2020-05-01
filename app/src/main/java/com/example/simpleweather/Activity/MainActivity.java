package com.example.simpleweather.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.simpleweather.Adapter.CityFragmentPagerAdapter;
import com.example.simpleweather.Fragment.CityWeatherBlankFragment;
import com.example.simpleweather.R;
import com.example.simpleweather.db.DBManeger;

import java.util.ArrayList;
import java.util.List;

import static com.example.simpleweather.db.DBManeger.initDB;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView addCityIv,searchIv;

    ViewPager mainVp;

    List<Fragment> fragmentList;
    List<String> cityList;

    CityFragmentPagerAdapter adapter;

    public final static int CITY_COUNT_MAX= 8;//最大查询城市数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        addCityIv=findViewById(R.id.main_iv_add);
        searchIv=findViewById(R.id.main_iv_search);

        mainVp=findViewById(R.id.main_vp);

        //添加点击事件
        addCityIv.setOnClickListener(this);
        searchIv.setOnClickListener(this);

        fragmentList=new ArrayList<>();


        /*初始化数据库*/
       initDB(this);



        /*获取数据库的城市信息列表*/
      cityList= DBManeger.queryAllCityName();


        if(cityList.size()==0){
            cityList.add("本地");//本ip地址的天气信息
        }

        /*搜索界面点击跳转此界面，传值，在此处获取*/
        Intent intent = getIntent();
        String city =intent.getStringExtra("city");
        if(!cityList.contains(city)&& !TextUtils.isEmpty(city)){
            cityList.add(city);
        }

        iniPager();

        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        mainVp.setAdapter(adapter);
        //显示最后一个页面
        mainVp.setCurrentItem(fragmentList.size()-1);


    }

    private void iniPager() {

        for(int i = 0 ;i<cityList.size();i++){
            CityWeatherBlankFragment cwFrag = new CityWeatherBlankFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
        }
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){
            case R.id.main_iv_add:
                intent.setClass(this, CityManegerActivity.class);
                startActivity(intent);
                break;

            case R.id.main_iv_search:
                int count = DBManeger.getCityCount();
                if(count<CITY_COUNT_MAX){
                    intent.setClass(this, SearchCityActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"存储城市数量已达上限，请删除后再添加",Toast.LENGTH_LONG).show();
                }
                break;
        }

    }


    /*页面重新加载时会调用的函数，此处完成页面的更新*/
    @Override
    protected void onRestart() {
        super.onRestart();
        //获取数据源当前的集合
        List<String> nowList = DBManeger.queryAllCityName();
        if(nowList.size()==0){
            nowList.add("本地");
        }
        cityList.clear();
        cityList.addAll(nowList);

        fragmentList.clear();
        iniPager();

        adapter.notifyDataSetChanged();
        mainVp.setCurrentItem(fragmentList.size()-1);

    }

}
