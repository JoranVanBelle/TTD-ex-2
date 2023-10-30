package org.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONException;
import org.json.JSONObject;

public record CompleteWeatherInformation(JSONObject apiResponse) {


    public String location() {
        try {
            return apiResponse
                    .getJSONObject("location")
                    .getString("name");

        } catch (JSONException e) {
            throw new IllegalArgumentException("Location not available");
        }
    }

    public double temperatureCelsius() {
        try {
            return apiResponse
                    .getJSONObject("current")
                    .getDouble("temp_c");

        } catch (JSONException e) {
            throw new IllegalArgumentException("Temperature in Celsius not available");
        }
    }

    public String condition() {
        try {
            return apiResponse
                    .getJSONObject("current")
                    .getJSONObject("condition")
                    .getString("text");

        } catch (JSONException e) {
            throw new IllegalArgumentException("Text of condition not available");
        }
    }

}
