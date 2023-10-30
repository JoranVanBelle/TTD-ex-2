package org.example.infra;

import com.github.tomakehurst.wiremock.http.Body;
import org.example.configuration.ApiProperties;
import org.example.extension.WireMockExtension;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.status;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.apache.commons.lang3.StringUtils.chop;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(WireMockExtension.class)
public class ApiScannerImplTest {

    @Autowired
    ApiProperties apiProperties;

    private String url;
    private String key;
    private String baseUrl;

    private static final String CITY = "lichtervelde";

    private ApiScannerImpl apiScanner;

    @BeforeEach
    void beforeEach() {
        apiScanner = new ApiScannerImpl();
        url = apiProperties.getUrl();
        key = apiProperties.getKey();
        baseUrl = apiProperties.getBaseUrl();
    }

    @Nested
    class WhenNothingIsWrong {

        @Test
        void thenAJsonObjectIsReturned() throws JSONException {

            stubFor(get(url)
                    .willReturn(ok()
                            .withHeader("Content-Type", "application/json")
                            .withResponseBody(new Body(getExpectedResponse()))));

            var result = apiScanner.getApiResponse(url, CITY);

            assertThat(result, is(equalTo(new JSONObject(getExpectedResponse()))));
        }

        private String getExpectedResponse() throws JSONException {
            return"""
                        {
                                    "location": {
                                        "name": "Lichtervelde",
                                        "region": "",
                                        "country": "Belgium",
                                        "lat": 51.03,
                                        "lon": 3.15,
                                        "tz_id": "Europe/Brussels",
                                        "localtime_epoch": 1698659221,
                                        "localtime": "2023-10-30 10:47"
                                    },
                                    "current": {
                                        "last_updated_epoch": 1698658200,
                                        "last_updated": "2023-10-30 10:30",
                                        "temp_c": 11.0,
                                        "temp_f": 51.8,
                                        "is_day": 1,
                                        "condition": {
                                            "text": "Moderate rain",
                                            "icon": "//cdn.weatherapi.com/weather/64x64/day/302.png",
                                            "code": 1189
                                        },
                                        "wind_mph": 8.1,
                                        "wind_kph": 13.0,
                                        "wind_degree": 140,
                                        "wind_dir": "SE",
                                        "pressure_mb": 999.0,
                                        "pressure_in": 29.5,
                                        "precip_mm": 0.0,
                                        "precip_in": 0.0,
                                        "humidity": 94,
                                        "cloud": 75,
                                        "feelslike_c": 8.7,
                                        "feelslike_f": 47.7,
                                        "vis_km": 10.0,
                                        "vis_miles": 6.0,
                                        "uv": 3.0,
                                        "gust_mph": 16.1,
                                        "gust_kph": 25.8
                                    }
                                }
                """;
        }

    }

    @Nested
    class WhenSomethingIsWrong {

        private String badUrl;

        @Nested
        class WhenKeyIsMissing {

            @Test
            void thenAnIllegalArgumentExceptionIsThrown() {
                badUrl = baseUrl + "?key&q=%s".formatted(CITY);

                assertThrows(IllegalArgumentException.class, () -> apiScanner.getApiResponse(badUrl, CITY));
            }
        }

        @Nested
        class WhenKeyIsWrong {

            @Test
            void thenAnIllegalArgumentExceptionIsThrown() {
                badUrl = baseUrl + "?key=badApiKey&q=%s".formatted(CITY);

                assertThrows(IllegalArgumentException.class, () -> apiScanner.getApiResponse(badUrl, CITY));
            }

        }

        @Nested
        class WhenUrlIsUnavailable {

            @Test
            void thenAnIllegalArgumentExceptionIsThrown() {

                badUrl = "%s?key=%s&q=%s".formatted(chop(baseUrl), key, CITY);

                assertThrows(IllegalArgumentException.class, () -> apiScanner.getApiResponse(badUrl, CITY));

            }

        }

    }

}
