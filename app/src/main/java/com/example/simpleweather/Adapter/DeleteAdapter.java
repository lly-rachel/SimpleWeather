package com.example.simpleweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpleweather.R;

import java.util.List;

public class DeleteAdapter extends BaseAdapter {

    Context context;
    List<String> mDatas;
    List<String> deleteCitys;

    public DeleteAdapter(Context context, List<String> mDatas, List<String> deleteCitys) {
        this.context = context;
        this.mDatas = mDatas;
        this.deleteCitys = deleteCitys;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_delete_city,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        final String city = mDatas.get(position);
        holder.citynameTv.setText(city);
        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(city);
                deleteCitys.add(city);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder{

        ImageView deleteIv;
        TextView citynameTv;

        public ViewHolder(View itemView){

            deleteIv=itemView.findViewById(R.id.city_delete_icon);
            citynameTv=itemView.findViewById(R.id.city_delete_cityname);

        }
    }
}
