package org.example.infra;

import org.example.configuration.ApiProperties;
import org.example.entity.CompleteWeatherInformation;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

public class ApiScannerImpl implements ApiScanner {

    private final ApiProperties properties;

    public ApiScannerImpl(ApiProperties properties) {
        this.properties = properties;
    }

    public CompleteWeatherInformation getApiResponse(String location) {
        return null;
    }
}
