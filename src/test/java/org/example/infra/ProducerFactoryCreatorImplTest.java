package org.example.infra;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProducerFactoryCreatorImplTest {

    private ProducerFactoryCreatorImpl factoryCreator;

    @BeforeEach
    void beforeEach() {
        this.factoryCreator = new ProducerFactoryCreatorImpl();
    }

    @Nested
    class FactoryCreation {

        @Nested
        class WhenPropertiesAreMissing {

            @Test
            public void thenAnErrorIsThrown() {
                assertThrows(NullPointerException.class, () -> factoryCreator.createProducerFactory(null));
            }

        }

        @Nested
        class PropertiesAreAvailable {

            private Map<String, Object> props;

            @BeforeEach
            void beforeEach() {
                props = new HashMap<>();
                props.put("bootstrap_servers", "localhost:9092");
                props.put("key_ser", StringSerializer.class);
                props.put("value_ser", KafkaAvroSerializer.class);
                props.put("sr", "http://localhost:8081");
            }

            @Test
            public void thenTheFactoryIsCreated() {
                assertNotNull(factoryCreator.createProducerFactory(props));
            }

        }
    }

}
