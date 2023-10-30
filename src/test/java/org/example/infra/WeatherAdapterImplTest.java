package org.example.infra;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.example.Contents.apiResponseAsJSONObject;
import static org.example.Contents.apiResponseWithoutCityAsJSONObject;
import static org.example.Contents.apiResponseWithoutConditionTextAsJSONObject;
import static org.example.Contents.apiResponseWithoutTempAsJSONObject;
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
            var weatherRegistered = weatherAdapter.getWeather(apiResponseAsJSONObject());

            assertThat(weatherRegistered, is(equalTo(expectedWeatherRegistered())));
        }

    }

    @Nested
    class WhenSomethingGoedWrong {

        @Nested
        class WhenNoObjectIsPassed {

            @Test
            void thenANoSuchElementIsThrown() {
                assertThrows(NoSuchElementException.class, () -> weatherAdapter.getWeather(null));
            }

        }

        @Nested
        class WhenWrongObjectPassed {

            @ParameterizedTest
            @MethodSource("wrongObjects")
            void thenIllegalArgumentIsThrown(JSONObject weatherRegistered) {
                assertThrows(IllegalArgumentException.class, () -> weatherAdapter.getWeather(weatherRegistered));
            }

            private static Stream<JSONObject> wrongObjects() throws JSONException {
                return Stream.of(
                        new JSONObject("{}"),
                        apiResponseWithoutCityAsJSONObject(),
                        apiResponseWithoutTempAsJSONObject(),
                        apiResponseWithoutConditionTextAsJSONObject()
                );
            }

        }

    }

}
