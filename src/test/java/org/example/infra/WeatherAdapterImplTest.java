package org.example.infra;

import org.example.entity.CompleteWeatherInformation;
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
            void thenIllegalArgumentIsThrown(CompleteWeatherInformation weatherRegistered) {
                assertThrows(IllegalArgumentException.class, () -> weatherAdapter.getWeather(weatherRegistered));
            }

            private static Stream<CompleteWeatherInformation> wrongObjects() throws JSONException {
                return Stream.of(
                        new CompleteWeatherInformation(new JSONObject("{}")),
                        apiResponseWithoutCity(),
                        apiResponseWithoutTemp(),
                        apiResponseWithoutConditionText()
                );
            }

        }

    }

}
