package org.example.infra;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.example.Person;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PersonProducerImpl implements PersonProducer{

    private static final String topic = "person";
    private final KafkaTemplate<String, Person> kafkaTemplate;

    public PersonProducerImpl(KafkaTemplate<String, Person> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendRecord(Person person) {
        kafkaTemplate.send(topic, person);
    }
}
