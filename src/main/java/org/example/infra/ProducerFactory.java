package org.example.infra;

import java.util.Map;
import java.util.Properties;

public interface ProducerFactory {

    org.springframework.kafka.core.ProducerFactory<String, Person> createProducer(Map<String, Object> props);

}
