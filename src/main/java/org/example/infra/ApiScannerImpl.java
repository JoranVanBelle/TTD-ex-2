package org.example.infra;

import org.example.configuration.ApiProperties;
import org.example.entity.WeatherInformation;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpStatus.OK;

public class ApiScannerImpl implements ApiScanner {

    private final ApiProperties properties;
    private final RestTemplate restTemplate;

    public ApiScannerImpl(ApiProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    public WeatherInformation getApiResponse(String location) {
        var responseEntity = restTemplate.getForEntity("%s%s".formatted(properties.getUrl(), location), WeatherInformation.class);

        return responseEntity.getStatusCode() == OK ? responseEntity.getBody() : null;
    }
}
