package org.example.infra;

import org.example.WeatherRegistered;
import org.example.entity.CompleteWeatherInformation;
import org.json.JSONObject;

public interface WeatherAdapter {

    WeatherRegistered getWeather(CompleteWeatherInformation weather);

}
