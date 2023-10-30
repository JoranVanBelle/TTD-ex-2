package org.example.infra;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProducerFactoryCreatorImplTest {

    private ProducerFactoryCreatorImpl producerFactoryCreator;
    private Map<String, Object> props;

    @BeforeEach
    void beforeEach() {
        this.producerFactoryCreator = new ProducerFactoryCreatorImpl();

    }

    @Nested
    class WhenPropertiesAreMissing {

        @Test
        void thenAnErrorIsThrown() {
            assertThrows(IllegalArgumentException.class, () -> producerFactoryCreator.createWeatherProducerFactory(null));
        }
    }

    @Nested
    class WhenPropertiesAreAvailable {

        @Nested
        class WhenSomePropertiesAreMissing {

            @ParameterizedTest
            @MethodSource("someParametersAreMissing")
            void thenAnErrorIsThrown(Map<String, Object> props) {
                assertThrows(NullPointerException.class, () -> producerFactoryCreator.createWeatherProducerFactory(props));
            }

            private static Stream<Map<String, Object>> someParametersAreMissing() {
                return Stream.of(
                        Map.of("bootstrap_servers", "localhost:9092", "key_ser", StringSerializer.class, "value_ser", KafkaAvroSerializer.class),
                        Map.of("bootstrap_servers", "localhost:9092", "key_ser", StringSerializer.class, "sr", "http://localhost:8081"),
                        Map.of("bootstrap_servers", "localhost:9092", "value_ser", KafkaAvroSerializer.class, "sr", "http://localhost:8081"),
                        Map.of( "key_ser", StringSerializer.class, "value_ser", KafkaAvroSerializer.class, "sr", "http://localhost:8081"),
                        Map.of("value_ser", KafkaAvroSerializer.class, "sr", "http://localhost:8081"),
                        Map.of("bootstrap.servers", "localhost:9092", "sr", "http://localhost:8081"),
                        Map.of("bootstrap.servers", "localhost:9092", "key_ser", StringSerializer.class),
                        Map.of( "key_ser", StringSerializer.class, "value_ser", KafkaAvroSerializer.class),
                        Map.of("bootstrap.servers", "localhost:9092", "sr", "http://localhost:8081"),
                        Map.of()
                );
            }

        }

        @Nested
        class WhenAllPropertiesAreAvailable {

            @BeforeEach
            void beforeEach() {
                props = new HashMap<>();
                props.put("bootstrap_servers", "localhost:9092");
                props.put("key_ser", StringSerializer.class);
                props.put("value_ser", KafkaAvroSerializer.class);
                props.put("sr", "http://localhost:8081");
            }

            @Test
            void thenFactoryIsCreated() {
                assertNotNull(producerFactoryCreator.createWeatherProducerFactory(props));
            }

        }

    }

}
