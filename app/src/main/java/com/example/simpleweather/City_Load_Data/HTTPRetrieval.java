package com.example.simpleweather.City_Load_Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRetrieval {

    public String HTTPWeatherGET(String city) {
        String res = "";

        StringBuilder sb = new StringBuilder();
        String urlString ="https://www.tianqiapi.com/free/day?appid=86838134&appsecret=gjBzX4SH";
        String cityString="&city="+city;
        String total_url;

        //若数据库城市信息为空，则只是url1的本地天气
        if(city.equals("本地")){
            total_url=urlString;
        }else{
            total_url=urlString+cityString;
        }

        try {
            // URL 是对 url 的处理类
            URL url = new URL(total_url);

            // 得到connection对象
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);

            //获取失败
            if(httpURLConnection.getResponseCode()!=HttpURLConnection.HTTP_OK){

                return null;

            }
            // 使用 InputStreamReader 进行数据接收
            InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream());
            // 缓存
            BufferedReader br = new BufferedReader(isr);

            String temp = null;
            // 读取接收的数据
            while ( (temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        res = sb.toString();



        return res;
    }

}
