package com.abdulaziz.winfo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CitySelector extends AppCompatActivity  {

    private Button goAsynButton;
    private Button goIntentButton;
    private Spinner citySpinner;
    private ArrayAdapter<String> cityList;
    private Context mContext;
    private String cityName;

    public static String[] cities = {"Chennai","Delhi","Hyderabad","Kolkata",
            "Bangalore","Mumbai","Pune","Chandigarh","London","New York"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selector);

        initUI();


    }

    private void initUI(){
        mContext = getApplicationContext();
        goAsynButton = (Button)findViewById(R.id.get_weather_async);
        goAsynButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName = citySpinner.getSelectedItem().toString();
                if(Helper.isDeviceOnline(mContext)){
                    System.out.println(cityName);
                    new WeatherTask().execute(cityName);
                }
            }
        });
        goIntentButton = (Button)findViewById(R.id.get_weather_intent);
        goIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName = citySpinner.getSelectedItem().toString();
                Intent weatherServiceIntent = new Intent(mContext,WeatherService.class);
                weatherServiceIntent.putExtra("CITY",cityName);
                startService(weatherServiceIntent);

            }
        });
        citySpinner = (Spinner)findViewById(R.id.city_list);
        cityList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        citySpinner.setAdapter(cityList);

    }



    public class WeatherTask extends AsyncTask<String , Void, String[]>{

        @Override
        protected String[] doInBackground(String... strings) {
            return Helper.getandParseWeatherInfo(strings[0]);

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String[] result) {
            if(result!=null){
                Intent weatherInfoIntent = new Intent(mContext,WeatherInfo.class);

                weatherInfoIntent.putExtra("CITY", cityName);
                weatherInfoIntent.putExtra("MAIN", result[0]);
                weatherInfoIntent.putExtra("DESCR", result[1]);
                weatherInfoIntent.putExtra("TEMP", result[2]);
                weatherInfoIntent.putExtra("HUMID", result[3]);
                startActivity(weatherInfoIntent);

            }
        }

    }
}
