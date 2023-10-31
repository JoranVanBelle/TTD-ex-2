package org.example.infra;

import org.example.WeatherRegistered;
import org.example.entity.WeatherInformation;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAdapterImpl implements WeatherAdapter {

    public WeatherRegistered getWeather(WeatherInformation weather) {

        return WeatherRegistered.newBuilder()
                .setLocation(weather.location())
                .setTempC(weather.temperature())
                .setCondition(weather.condition())
                .build();
    }
}
