package org.example.infra;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.example.Person;

public class PersonProducerImpl implements PersonProducer{

    private KafkaProducer<String, Person> producer;

    public PersonProducerImpl(KafkaProducer<String, Person> producer) {
        this.producer = producer;
    }

    public void sendRecord(Person person) {
    }
}
