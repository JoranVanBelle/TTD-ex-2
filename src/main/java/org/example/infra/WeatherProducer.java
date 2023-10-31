package org.example.infra;

import org.example.WeatherRegistered;

public interface WeatherProducer {

    void sendRecord(WeatherRegistered weather);

}
