package org.example.infra;

import org.example.Person;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

public interface ProducerFactoryCreator {

    ProducerFactory<String, Person> createProducerFactory(Map<String, Object> props);

}
