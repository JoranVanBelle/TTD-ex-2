package org.example.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public record WeatherInformation(String location, Double temperature, String condition) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherInformation that = (WeatherInformation) o;
        return Objects.equals(location, that.location) && Objects.equals(temperature, that.temperature) && Objects.equals(condition, that.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, temperature, condition);
    }

    public static WeatherInformation of(JSONObject object ) {

        String location;
        double temperature;
        String condition;

        try {

            location = object
                    .getJSONObject("location")
                    .getString("name");

            temperature = object
                    .getJSONObject("current")
                    .getDouble("temp_c");

            condition = object
                    .getJSONObject("current")
                    .getJSONObject("condition")
                    .getString("text");

            return new WeatherInformation(location, temperature, condition);
    } catch (JSONException e) {
        throw new IllegalArgumentException("One or more of the arguments are not available");
    }
    }

}
