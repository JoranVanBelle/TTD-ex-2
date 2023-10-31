package org.example.infra;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.example.configuration.ApiProperties;
import org.example.extension.WireMockExtension;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.unauthorized;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.example.Contents.apiResponse;
import static org.example.Contents.apiResponseAsBody;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@ExtendWith(WireMockExtension.class)
public class ApiScannerImplTest {

    @Autowired
    private ApiProperties apiProperties;

    @MockBean
    private RestTemplate restTemplate;

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

            when(restTemplate.getForEntity(anyString(), any()))
                    .thenReturn(new ResponseEntity<>(apiResponse(), OK));

            WireMockExtension.wireMockServer.stubFor(get(baseUrl)
                    .withQueryParam("key", WireMock.equalTo(key))
                    .withQueryParam("q", WireMock.equalTo(CITY))
                    .willReturn(ok()
                            .withHeader("Content-Type", "application/json")
                            .withResponseBody(apiResponseAsBody())));

            var result = apiScanner.getApiResponse(restTemplate, CITY);

            assertThat(result, equalTo(apiResponse()));
        }

    }


    @Nested
    class WhenSomethingIsWrong {

        @Nested
        class WhenKeyIsMissing {

            @Test
            void thenAnIllegalArgumentExceptionIsThrown() {

                WireMockExtension.wireMockServer.stubFor(get(baseUrl)
                        .withQueryParam("q", WireMock.equalTo(CITY))
                        .willReturn(unauthorized()
                                .withStatus(401)));

                assertThrows(NullPointerException.class, () -> apiScanner.getApiResponse(restTemplate, CITY));
            }
        }

        @Nested
        class WhenKeyIsWrong {

            @Test
            void thenAnIllegalArgumentExceptionIsThrown() {

                WireMockExtension.wireMockServer.stubFor(get(baseUrl)
                        .withQueryParam("key", WireMock.equalTo("badApiKey"))
                        .withQueryParam("q", WireMock.equalTo(CITY))
                        .willReturn(unauthorized()
                                .withStatus(401)));

                assertThrows(NullPointerException.class, () -> apiScanner.getApiResponse(restTemplate, CITY));
            }

        }

        @Nested
        class WhenUrlIsUnavailable {

            @Test
            void thenAnIllegalArgumentExceptionIsThrown() {

                WireMockExtension.wireMockServer.stubFor(get(baseUrl)
                        .withQueryParam("key", WireMock.equalTo(key))
                        .withQueryParam("q", WireMock.equalTo(CITY))
                        .willReturn(notFound()
                                .withStatus(404)));

                assertThrows(NullPointerException.class, () -> apiScanner.getApiResponse(restTemplate, CITY));

            }

        }

    }


}
