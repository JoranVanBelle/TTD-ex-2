package org.example.infra;

import org.example.WeatherRegistered;
import org.example.entity.CompleteWeatherInformation;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAdapterImpl implements WeatherAdapter {

    public WeatherRegistered getWeather(CompleteWeatherInformation weather) {

        return WeatherRegistered.newBuilder()
                .setLocation(weather.location())
                .setTempC(weather.temperatureCelsius())
                .setCondition(weather.condition())
                .build();
    }
}
