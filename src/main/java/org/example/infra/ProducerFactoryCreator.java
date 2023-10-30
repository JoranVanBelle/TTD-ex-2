package org.example.infra;

import org.example.Person;
import org.example.WeatherRegistered;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

public interface ProducerFactoryCreator {

    ProducerFactory<String, Person> createPersonProducerFactory(Map<String, Object> props);

    ProducerFactory<String, WeatherRegistered> createWeatherProducerFactory(Map<String, Object> props);

}
