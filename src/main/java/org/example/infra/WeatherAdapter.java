package org.example.infra;

import org.example.WeatherRegistered;
import org.example.entity.WeatherInformation;
import org.json.JSONObject;

public interface WeatherAdapter {

    WeatherRegistered getWeather(WeatherInformation weather);

}
