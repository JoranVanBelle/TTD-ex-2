package org.example.infra;

import java.util.Map;

public interface ProducerFactoryCreator {

    org.springframework.kafka.core.ProducerFactory<String, Person> createProducerFactory(Map<String, Object> props);

}
