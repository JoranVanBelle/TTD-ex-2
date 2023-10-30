package org.example.infra;

import org.example.WeatherRegistered;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WeatherProducerImplTest {

    @Mock
    private KafkaTemplate<String, WeatherRegistered> kafkaTemplate;

    private WeatherProducerImpl weatherProducer;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.weatherProducer = new WeatherProducerImpl(kafkaTemplate);
    }

    @Nested
    class WhenNothingGoesWrong {

        @Test
        void thenTheMessageIsSentOnce() {

            var expectedWeather = WeatherRegistered.newBuilder()
                    .setLocation("lichtervelde")
                    .setTempC(11.2)
                    .setCondition("moderate rain")
                    .build();

            verify(kafkaTemplate, times(1)).send(eq("weather"), eq(expectedWeather));

        }

    }

}
