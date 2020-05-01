package com.example.simpleweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DBManeger {

    public static SQLiteDatabase database;
    /*初始化数据库信息*/
    public static void initDB(Context context){
        DBhelper dbHelper = new DBhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /*查找数据库当中城市列表*/
    public static List<String> queryAllCityName(){
        Cursor cursor = database.query("info",null,null,null,null,null,null);
        List<String> citylist = new ArrayList<>();
        while(cursor.moveToNext()){
            String city = cursor.getString(cursor.getColumnIndex("city"));
            citylist.add(city);
        }
        return citylist;
    }

    /*根据城市名称替换信息内容*/
    public static int updateInfoByCity(String city,String content){
        ContentValues values = new ContentValues();
        values.put("content",content);
        return database.update("info",values,"city=?",new String[]{city});
    }

    /*新增一条城市信息*/
    public static long addCityInfo(String city,String content){
        ContentValues values = new ContentValues();
        values.put("city",city);
        values.put("content",content);
        return database.insert("info",null,values);
    }

    /*根据城市名查询数据库当中的内容*/
    public static String queryInroByCity(String city){
        Cursor cursor = database.query("info",null,"city=?",new String[]{city},null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            String content=cursor.getString(cursor.getColumnIndex("content"));
            return content;
        }
        return null;
    }

    /*返回当前表格城市具体个数*/
    public static int getCityCount(){
        Cursor cursor = database.query("info",null,null,null,null,null,null);
        return cursor.getCount();

    }

    /*查询数据库所有信息*/
    public static List<DataBaseBean> queryAllInfo(){
        Cursor cursor = database.query("info",null,null,null,null,null,null);
        List<DataBaseBean> list = new ArrayList<>();
       while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            DataBaseBean bean = new DataBaseBean(id,city,content);
            list.add(bean);
        }
        return list;
    }

    /*根据城市名称，删除数据库的数据*/
    public static int deleteInfoByCity(String city){
        return database.delete("info","city=?",new String[]{city});
    }
}
