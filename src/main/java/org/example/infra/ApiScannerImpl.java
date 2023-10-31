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

    public ApiScannerImpl(ApiProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    public WeatherInformation getApiResponse(RestTemplate restTemplate, String location) {
        var responseEntity = restTemplate.getForEntity("%s%s".formatted(properties.getUrl(), location), WeatherInformation.class);

        return responseEntity.getStatusCode() == OK ? responseEntity.getBody() : null;
    }
}
