package club.playerfox.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import club.playerfox.coolweather.db.City;
import club.playerfox.coolweather.db.County;
import club.playerfox.coolweather.db.Province;
import club.playerfox.coolweather.gson.Weather;

public class Utility {

    /**
     * 解析省级数据
     *
     * @param response 服务器响应数据
     * @return 是否成功获取
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray all = new JSONArray(response);
                for (int i = 0; i < all.length(); i++) {
                    JSONObject object = all.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(object.getString("name"));
                    province.setProvinceCode(object.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析市级数据
     *
     * @param response   服务器响应数据
     * @param provinceId 省id
     * @return 是否成功获取
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray all = new JSONArray(response);
                for (int i = 0; i < all.length(); i++) {
                    JSONObject object = all.getJSONObject(i);
                    City city = new City();
                    city.setCityName(object.getString("name"));
                    city.setCityCode(object.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                Log.e("Error:", "handleCityResponse: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * 解析县级数据
     *
     * @param response 服务器响应数据
     * @param cityId   城市id
     * @return 是否成功获取
     */
    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray all = new JSONArray(response);
                for (int i = 0; i < all.length(); i++) {
                    JSONObject object = all.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(object.getString("name"));
                    county.setWeatherId(object.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                Log.e("County", e.getMessage());
            }
        }
        return false;
    }


    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject object = new JSONObject(response);
            JSONArray array = object.getJSONArray("HeWeather");
            String weatherContent = array.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return null;
    }
}
