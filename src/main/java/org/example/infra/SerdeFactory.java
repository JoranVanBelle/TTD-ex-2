package org.example.infra;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.example.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SerdeFactory {

    @Value("${spring.kafka.properties.schema.registry.url}")
    private static String schema_registry;

    public static SpecificAvroSerde<Person> personSerde(){

        System.out.println("here");
        System.out.println(schema_registry);

        final SpecificAvroSerde<Person> personSerde = new SpecificAvroSerde<>();
        Map<String, String> config = new HashMap<>();
        config.put(SCHEMA_REGISTRY_URL_CONFIG, schema_registry);
        personSerde.configure(config, false);
        return personSerde;
    }

}
