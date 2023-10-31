package org.example.infra;

import org.example.WeatherRegistered;
import org.example.entity.WeatherInformation;

public interface WeatherAdapter {

    WeatherRegistered getWeather(WeatherInformation weather);

}
