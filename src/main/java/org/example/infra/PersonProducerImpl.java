package org.example.infra;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.example.Person;

public class PersonProducerImpl implements PersonProducer{

    private Producer<String, Person> producer;

    public PersonProducerImpl(Producer<String, Person> producer) {
        this.producer = producer;
    }

    public void sendRecord(Person person) {
    }
}
