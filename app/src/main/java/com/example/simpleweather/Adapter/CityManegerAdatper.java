package com.example.simpleweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.simpleweather.R;
import com.example.simpleweather.City_Load_Data.JsonParser;
import com.example.simpleweather.City_Load_Data.WeatherInfo;
import com.example.simpleweather.db.DataBaseBean;

import java.util.List;

public class CityManegerAdatper extends BaseAdapter {

    Context context;
    List<DataBaseBean> mDatas;

    public CityManegerAdatper(Context context, List<DataBaseBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_city_maneger,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        DataBaseBean bean = mDatas.get(position);

        JsonParser jp =new JsonParser();
        WeatherInfo wi =jp.WeatherParse(bean.getContent());

        holder.cityTv.setText(bean.getCity());

        holder.temTv.setText(wi.getTem()+"℃");
        holder.uptimgTv.setText(wi.getUpdate_time());
        return convertView;
    }



    class ViewHolder{
        TextView cityTv,temTv,uptimgTv;

        public ViewHolder(View itemview){
            cityTv=itemview.findViewById(R.id.city_maneger_cityname);
            uptimgTv=itemview.findViewById(R.id.city_maneger_cityupdatetime);
            temTv=itemview.findViewById(R.id.city_maneger_tem);
        }
    }
}
