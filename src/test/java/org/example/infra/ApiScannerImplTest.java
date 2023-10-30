package org.example.infra;

import org.example.configuration.ApiProperties;
import org.example.extension.WireMockExtension;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.apache.commons.lang3.StringUtils.chop;
import static org.example.Contents.apiResponseAsBody;
import static org.example.Contents.apiResponseAsJSONObject;
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
                            .withResponseBody(apiResponseAsBody())));

            var result = apiScanner.getApiResponse(url, CITY);

            assertThat(result, is(equalTo(apiResponseAsJSONObject())));
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
