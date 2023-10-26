package org.example.infra;

import org.apache.kafka.clients.producer.Producer;
import org.example.Person;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

public class ProducerFactoryCreatorImpl implements ProducerFactoryCreator {

    public ProducerFactory<String, Person> createProducerFactory(Map<String, Object> props) {
        return null;
    }
}
