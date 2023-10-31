package org.example.infra;

import org.example.entity.WeatherInformation;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public interface ApiScanner {

    WeatherInformation getApiResponse(RestTemplate restTemplate, String location);

}
