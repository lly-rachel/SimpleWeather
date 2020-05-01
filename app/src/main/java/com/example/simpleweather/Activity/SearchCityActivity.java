package com.example.simpleweather.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.simpleweather.R;
import com.example.simpleweather.db.DBManeger;

public class SearchCityActivity extends AppCompatActivity implements View.OnClickListener {

    EditText citySearchEt;
    ImageView searchIv;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        citySearchEt=findViewById(R.id.city_search_editview);
        searchIv=findViewById(R.id.city_search);
        searchIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.city_search:

                city = citySearchEt.getText().toString();

                if(!TextUtils.isEmpty(city)){

                    //数据库中查找上一次信息显示在Fragment当中
                    String s= DBManeger.queryInroByCity(city);
                    if(TextUtils.isEmpty(s)){
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("city",city);
                        startActivity(intent);

                    }else{
                        Toast.makeText(this,"该城市已存在",Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(this,"输入城市不能为空",Toast.LENGTH_LONG).show();
                }

                break;

        }


    }
}
