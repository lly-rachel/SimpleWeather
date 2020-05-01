package com.example.simpleweather.City_Load_Data;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public WeatherInfo WeatherParse (String weatherString) {
        WeatherInfo weatherInfo=new WeatherInfo();
        try {
            JSONObject joWeather = new JSONObject(weatherString);

            weatherInfo.setCity(joWeather.getString("city"));
            weatherInfo.setAir(joWeather.getString("air"));
            weatherInfo.setCityid(joWeather.getString("cityid"));
            weatherInfo.setUpdate_time(joWeather.getString("update_time"));
            weatherInfo.setWea(joWeather.getString("wea"));
            weatherInfo.setWea_img(joWeather.getString("wea_img"));
            weatherInfo.setTem(joWeather.getString("tem"));
            weatherInfo.setTem_day(joWeather.getString("tem_day"));
            weatherInfo.setTem_night(joWeather.getString("tem_night"));
            weatherInfo.setWin(joWeather.getString("win"));
            weatherInfo.setWin_speed(joWeather.getString("win_speed"));
            weatherInfo.setWin_meter(joWeather.getString("win_meter"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherInfo;
    }

}
