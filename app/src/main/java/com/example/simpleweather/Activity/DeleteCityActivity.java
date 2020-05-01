package com.example.simpleweather.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.simpleweather.R;
import com.example.simpleweather.db.DBManeger;
import com.example.simpleweather.Adapter.DeleteAdapter;

import java.util.ArrayList;
import java.util.List;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView noIv,sureIv;
    ListView deleteLv;
    List<String> cities;
    List<String> deletecity;
    DeleteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);

        noIv=findViewById(R.id.delete_city_no);
        sureIv=findViewById(R.id.delete_city_sure);
        deleteLv=findViewById(R.id.delete_city_list);

        //设置数据库信息
        cities = DBManeger.queryAllCityName();
        deletecity = new ArrayList<>();

        noIv.setOnClickListener(this);
        sureIv.setOnClickListener(this);

        adapter=new DeleteAdapter(this,cities,deletecity);
        deleteLv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.delete_city_no:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示信息").setMessage("确定舍弃更改？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish(); //关闭当前activity
                            }
                        });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;

            case R.id.delete_city_sure:

                for(int i=0;i<deletecity.size();i++){
                    String city = deletecity.get(i);
                    DBManeger.deleteInfoByCity(city);
                }
                //返回上一级
                finish();
                break;
        }


    }
}
