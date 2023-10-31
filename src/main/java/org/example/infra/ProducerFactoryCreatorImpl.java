package org.example.infra;

import org.apache.kafka.clients.producer.Producer;
import org.example.Person;
import org.example.WeatherRegistered;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

public class ProducerFactoryCreatorImpl implements ProducerFactoryCreator {

    @Bean
    public ProducerFactory<String, Person> createPersonProducerFactory(Map<String, Object> props) {
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public ProducerFactory<String, WeatherRegistered> createWeatherProducerFactory(Map<String, Object> props) {
}
