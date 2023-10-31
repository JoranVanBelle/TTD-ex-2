package org.example.infra;

import org.example.entity.WeatherInformation;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.example.Contents.apiResponse;
import static org.example.Contents.apiResponseWithoutCity;
import static org.example.Contents.apiResponseWithoutConditionText;
import static org.example.Contents.apiResponseWithoutTemp;
import static org.example.Contents.expectedWeatherRegistered;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeatherAdapterImplTest {

    private WeatherAdapterImpl weatherAdapter;

    @BeforeEach
    void beforeEach() {
        this.weatherAdapter = new WeatherAdapterImpl();
    }

    @Nested
    class WhenNothingGoesWrong {

        @Test
        void thenAWeatherObjectIsReturned() throws JSONException {
            var weatherRegistered = weatherAdapter.getWeather(apiResponse());

            assertThat(weatherRegistered, is(equalTo(expectedWeatherRegistered())));
        }

    }

}
