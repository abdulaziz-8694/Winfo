package com.abdulaziz.winfo;

import android.app.IntentService;
import android.content.Intent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class WeatherService extends IntentService {
    private String cityName;

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            cityName = intent.getStringExtra("CITY");

            String[] result = Helper.getandParseWeatherInfo(cityName);
            Intent weatherInfoIntent = new Intent(getBaseContext(),WeatherInfo.class);

            weatherInfoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            weatherInfoIntent.putExtra("CITY", cityName);
            weatherInfoIntent.putExtra("MAIN", result[0]);
            weatherInfoIntent.putExtra("DESCR", result[1]);
            weatherInfoIntent.putExtra("TEMP", result[2]);
            weatherInfoIntent.putExtra("HUMID", result[3]);
            startActivity(weatherInfoIntent);

        }
    }


}
