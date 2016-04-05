package com.tan.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tan.coolweather.model.City;
import com.tan.coolweather.model.County;
import com.tan.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2016/4/3.
 */
public class CoolWeatherDB {

    public static final String DB_NAME = "cool_weather";

    public static final int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;

    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static CoolWeatherDB getInstance(Context context){
        if(coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }

        return coolWeatherDB;
    }

    public void saveProvince(Province province){
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String provinceName = cursor.getString(cursor.getColumnIndex("province_name"));
                String provinceCode = cursor.getString(cursor.getColumnIndex("province_code"));

                province.setId(id);
                province.setProvinceName(provinceName);
                province.setProvinceCode(provinceCode);
                list.add(province);
            }while(cursor.moveToNext());
        }

        if(cursor != null){
            cursor.close();
        }

        return list;
    }

    public void saveCity(City city){
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String cityName = cursor.getString(cursor.getColumnIndex("city_name"));
                String cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
                city.setId(id);
                city.setCityName(cityName);
                city.setCityCode(cityCode);
                city.setProvinceId(provinceId);
                list.add(city);
            }while(cursor.moveToNext());
        }

        if(cursor != null){
            cursor.close();
        }

        return list;
    }

    public void saveCounty(County county){
        if(county != null){
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)},null, null, null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String countyName = cursor.getString(cursor.getColumnIndex("county_name"));
                String countyCode = cursor.getString(cursor.getColumnIndex("county_code"));
                county .setId(id);
                county.setCountyName(countyName);
                county.setCountyCode(countyCode);
                county.setCityId(cityId);

                list.add(county);

            }while(cursor.moveToNext());
        }

        if(cursor != null){
            cursor.close();
        }

        return list;
    }



}
