package com.abdulaziz.winfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WeatherInfo extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        Intent intent = getIntent();
        if(intent!=null){
            String City = intent.getStringExtra("CITY");
            String mainWeather = intent.getStringExtra("MAIN");
            String description = intent.getStringExtra("DESCR");
            String temperature = intent.getStringExtra("TEMP");
            if(temperature!=null){
                Double temp= (Double.parseDouble(temperature)-273.0);
                temperature = String.format("%s'C", String.format("%.2g", temp));
            }
            String humidity = intent.getStringExtra("HUMID");
            if(humidity!=null){
                humidity+="%";
            }
            ((TextView)findViewById(R.id.city_name))
                    .setText(City!=null?City:"");
            ((TextView)findViewById(R.id.main_weather))
                    .setText(mainWeather!=null?mainWeather:"");
            ((TextView)findViewById(R.id.weather_description))
                    .setText(description!=null?description:"");
            ((TextView)findViewById(R.id.temperature))
                    .setText(temperature!=null?temperature:"");
            ((TextView)findViewById(R.id.humidity))
                    .setText(humidity!=null?humidity:"");

        }
    }
}
