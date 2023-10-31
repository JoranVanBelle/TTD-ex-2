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
    class WhenPropertiesAreAvailable {

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
