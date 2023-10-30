package org.example.infra;

import org.example.WeatherRegistered;
import org.json.JSONObject;

public interface WeatherAdapter {

    WeatherRegistered getWeather(JSONObject weather);

}
