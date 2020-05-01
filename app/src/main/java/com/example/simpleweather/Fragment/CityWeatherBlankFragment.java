package com.example.simpleweather.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpleweather.City_Load_Data.HTTPRetrieval;
import com.example.simpleweather.City_Load_Data.JsonParser;
import com.example.simpleweather.City_Load_Data.WeatherInfo;
import com.example.simpleweather.R;
import com.example.simpleweather.db.DBManeger;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityWeatherBlankFragment extends Fragment {

    TextView tempTv, cityTv, conditionTv, dateTv, windTv;
    TextView airtxTv, speedtxTv, classtxTv, daytemtxTv, nighttemtxTv;
    TextView airTv, speedTv, classTv, daytemTv, nighttemTv;
    ImageView picture;

    Handler hd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather_blank, container, false);
        iniView(view);

        hd = new Handler();

        // 启用网络线程
        HttpThread ht = new HttpThread();
        ht.start();

        return view;
    }

    public class HttpThread extends Thread {
        //可以通过activity传值获取当前fragment加载的是哪个地方的天气情况
        Bundle bundle = getArguments();
        String city= bundle.getString("city");

        @Override
        public void run() {
            super.run();
            WeatherInfo wi = null;
            JsonParser jp = new JsonParser();

            HTTPRetrieval hr = new HTTPRetrieval();
            // 城市代码
            String weatherString = hr.HTTPWeatherGET(city);

            if(!weatherString.isEmpty()){
                // 调用自定义的 JSON 解析类解析获取的 JSON 数据
                wi= jp.WeatherParse(weatherString);

                //更新数据库信息
                int i= DBManeger.updateInfoByCity(city,weatherString);
                //更新数据库失败，说明数据库没有这个信息，添加这个城市天气信息
                if(i<=0) {
                    DBManeger.addCityInfo(city, weatherString);
                }

                final WeatherInfo finalWi = wi;
                // 多线程更新 UI
                hd.post(new Runnable() {
                    @Override
                    public void run() {

                        setDataText(finalWi);
                    }
                });
            }



        }
    }





    public void setDataText(WeatherInfo weatherbean) {

        tempTv.setText(weatherbean.getTem()+"℃");
        cityTv.setText(weatherbean.getCity());
        conditionTv.setText(weatherbean.getWea());
        dateTv.setText(weatherbean.getUpdate_time());
        windTv.setText(weatherbean.getWin());

        airTv.setText(weatherbean.getAir());
        speedTv.setText(weatherbean.getWin_meter());
        classTv.setText(weatherbean.getWin_speed());
        daytemTv.setText(weatherbean.getTem_day());
        nighttemTv.setText(weatherbean.getTem_night());

        airtxTv.setText("空气质量:");
        speedtxTv.setText("风速:");
        classtxTv.setText("风力等级:");
        daytemtxTv.setText("白天最高温:");
        nighttemtxTv.setText("晚上最低温:");

        picture.setImageResource(R.mipmap.weather);
    }

    public void iniView(View view) {
        //主要天气情况
        tempTv=view.findViewById(R.id.frag_tv_current_temper);
        cityTv=view.findViewById(R.id.frag_tv_current_city);
        conditionTv=view.findViewById(R.id.frag_tv_current_condition);
        dateTv=view.findViewById(R.id.frag_tv_current_date);
        windTv=view.findViewById(R.id.frag_tv_current_wind);

        //显示文本
        airtxTv=view.findViewById(R.id.airtext);
        speedtxTv=view.findViewById(R.id.speedtext);
        classtxTv=view.findViewById(R.id.classtext);
        daytemtxTv=view.findViewById(R.id.tem_daytext);
        nighttemtxTv=view.findViewById(R.id.tem_nighttext);

        //天气细节情况
        airTv=view.findViewById(R.id.frag_tv_air);
        speedTv=view.findViewById(R.id.frag_tv_speed);
        classTv=view.findViewById(R.id.frag_tv_windclass);
        daytemTv=view.findViewById(R.id.frag_tv_tem_day);
        nighttemTv=view.findViewById(R.id.frag_tv_tem_night);

        //图片
        picture=view.findViewById(R.id.iv_picture);
    }


}
