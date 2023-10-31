package org.example.configuration;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.example.Person;
import org.example.PersonWeatherJoined;
import org.example.WeatherRegistered;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SerdeFactory {

    public static SpecificAvroSerde<Person> personSerde(String schema_registry){

        System.out.printf("sr: %s%n", schema_registry);

        final SpecificAvroSerde<Person> personSerde = new SpecificAvroSerde<>();
        Map<String, String> config = new HashMap<>();
        config.put("schema.registry.url", schema_registry);
        personSerde.configure(config, false);
        return personSerde;
    }

    public static SpecificAvroSerde<WeatherRegistered> weatherSerde(String schema_registry){

        final SpecificAvroSerde<WeatherRegistered> weatherSerde = new SpecificAvroSerde<>();
        Map<String, String> config = new HashMap<>();
        config.put(SCHEMA_REGISTRY_URL_CONFIG, schema_registry);
        weatherSerde.configure(config, false);
        return weatherSerde;
    }

    public static SpecificAvroSerde<PersonWeatherJoined> personWeatherSerde(String schema_registry) {
        final SpecificAvroSerde<PersonWeatherJoined> joinedSerde = new SpecificAvroSerde<>();
        Map<String, String> config = new HashMap<>();
        config.put(SCHEMA_REGISTRY_URL_CONFIG, schema_registry);
        joinedSerde.configure(config, false);
        return joinedSerde;
    }

}
