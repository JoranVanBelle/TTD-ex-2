package org.example.infra;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.example.configuration.ApiProperties;
import org.example.extension.WireMockExtension;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.unauthorized;
import static org.apache.commons.lang3.StringUtils.chop;
import static org.example.Contents.apiResponseAsBody;
import static org.example.Contents.apiResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(WireMockExtension.class)
public class ApiScannerImplTest {

    @Autowired
    ApiProperties apiProperties;

    private String key;
    private String baseUrl;

    private static final String CITY = "lichtervelde";

    private ApiScannerImpl apiScanner;

    @BeforeEach
    void beforeEach() {
        apiScanner = new ApiScannerImpl(apiProperties);
        key = apiProperties.getKey();
        baseUrl = apiProperties.getBaseUrl();
    }

    @Nested
    class WhenNothingIsWrong {

        @Test
        void thenAJsonObjectIsReturned() throws JSONException {

            stubFor(get(baseUrl)
                    .withQueryParam("key", WireMock.equalTo(key))
                    .withQueryParam("q", WireMock.equalTo(CITY))
                    .willReturn(ok()
                            .withHeader("Content-Type", "application/json")
                            .withResponseBody(apiResponseAsBody())));

            var result = apiScanner.getApiResponse(CITY);

            assertThat(result, is(equalTo(apiResponse())));
        }

    }

    @Nested
    class WhenSomethingIsWrong {

        private String badUrl;

        @Nested
        class WhenKeyIsMissing {

            @Test
            void thenAnIllegalArgumentExceptionIsThrown() {

                stubFor(get(baseUrl)
                        .withQueryParam("q", WireMock.equalTo(CITY))
                        .willReturn(unauthorized()
                                .withStatus(401)));

                assertThrows(IllegalArgumentException.class, () -> apiScanner.getApiResponse(CITY));
            }
        }

        @Nested
        class WhenKeyIsWrong {

            @Test
            void thenAnIllegalArgumentExceptionIsThrown() {

                stubFor(get(baseUrl)
                        .withQueryParam("key", WireMock.equalTo("badApiKey"))
                        .withQueryParam("q", WireMock.equalTo(CITY))
                        .willReturn(unauthorized()
                                .withStatus(401)));

                assertThrows(IllegalArgumentException.class, () -> apiScanner.getApiResponse(CITY));
            }

        }

        @Nested
        class WhenUrlIsUnavailable {

            @Test
            void thenAnIllegalArgumentExceptionIsThrown() {

                stubFor(get(baseUrl)
                        .withQueryParam("key", WireMock.equalTo(key))
                        .withQueryParam("q", WireMock.equalTo(CITY))
                        .willReturn(notFound()
                                .withStatus(404)));

                assertThrows(IllegalArgumentException.class, () -> apiScanner.getApiResponse(CITY));

            }

        }

    }

}
