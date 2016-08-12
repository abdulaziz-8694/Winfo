package com.abdulaziz.winfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by abdulaziz on 12/08/16.
 */

public class Helper {

    public static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?";
    public static final String API_KEY = "44a4a420c126ece841674e44b52378cc";

    public static boolean isDeviceOnline(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.
                    getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

    public static String getWeatherInfo(String url){
        URL movieURL = null;
        HttpURLConnection urlConnection = null;
        String result = "";
        try {
            movieURL = new URL(url);
            urlConnection = (HttpURLConnection) movieURL.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            StringBuilder response = new StringBuilder();
            BufferedReader inReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = inReader.readLine()) != null){
                response.append(line);
            }
            result = response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = "{\"Response\": \"False\", \"Error\" : \"URL wrong\"}";


        } catch (IOException e) {
            e.printStackTrace();
            result = "{\"Response\": \"False\", \"Error\" : \"Network Error\"}";

        }
        finally {
            return result;
        }
    }

    public static String[] getandParseWeatherInfo(String cityName){
        JSONObject weatherData = null;
        String result[] = null;
        try {
            ;
            System.out.println("City = "+ cityName);

            String URL = Helper.API_URL + "q="+ URLEncoder.encode(cityName,"UTF-8")
                    +"&appid="+URLEncoder.encode(Helper.API_KEY,"UTF-8");
            String response = Helper.getWeatherInfo(URL);
            weatherData = new JSONObject(response);

            JSONArray weatherArray =  weatherData.getJSONArray("weather");

            JSONObject weather = weatherArray.getJSONObject(0);
            String mainWeather = weather.getString("main");
            String weatherDescription = weather.getString("description");

            JSONObject details = weatherData.getJSONObject("main");
            String temperature = details.getString("temp");
            String humidity = details.getString("humidity");
            result = new String[]{mainWeather, weatherDescription, temperature, humidity};

        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

}
