package com.example.simpleweather.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simpleweather.Adapter.CityManegerAdatper;
import com.example.simpleweather.R;
import com.example.simpleweather.db.DBManeger;
import com.example.simpleweather.db.DataBaseBean;

import java.util.ArrayList;
import java.util.List;

import static com.example.simpleweather.Activity.MainActivity.CITY_COUNT_MAX;

public class CityManegerActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView addIv,backIv,deleteIv;
    ListView cityLv;
    List<DataBaseBean> mDatas;
    private CityManegerAdatper adatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_maneger);
        addIv=findViewById(R.id.city_iv_add);
        backIv=findViewById(R.id.city_iv_back);
        deleteIv=findViewById(R.id.city_iv_delete);
        cityLv=findViewById(R.id.city_list);

        mDatas = new ArrayList<>();

        addIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);

        //设置适配器
        adatper= new CityManegerAdatper(this,this.mDatas);
        cityLv.setAdapter(adatper);
    }

    /*获取数据库当前真实数据，提示适配器更新*/
    @Override
    protected void onResume() {
        super.onResume();
        List<DataBaseBean> list = DBManeger.queryAllInfo();
        mDatas.clear();
        mDatas.addAll(list);
        adatper.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.city_iv_add:

                int count = DBManeger.getCityCount();
                if(count<CITY_COUNT_MAX){
                    Intent intent=new Intent(this, SearchCityActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"存储城市数量已达上限，请删除后再添加",Toast.LENGTH_LONG).show();

                }

                break;

            case R.id.city_iv_delete:
                Intent intent1=new Intent(this, DeleteCityActivity.class);
                startActivity(intent1);
                break;

            case R.id.city_iv_back:
                finish();
                break;
        }

    }
}
