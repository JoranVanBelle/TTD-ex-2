package org.example.infra;

public interface PersonProducer {

    KafkaTemplate<String, Person> kafkaTemplate();

    void sendRecord(Person person);

}
