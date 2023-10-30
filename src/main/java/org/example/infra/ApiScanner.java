package org.example.infra;

import org.example.entity.CompleteWeatherInformation;
import org.json.JSONObject;

public interface ApiScanner {

    CompleteWeatherInformation getApiResponse(String location);

}
