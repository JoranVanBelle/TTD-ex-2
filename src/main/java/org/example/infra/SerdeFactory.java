package org.example.infra;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.example.Person;

public interface SerdeFactory {

    static SpecificAvroSerde<Person> personSerde(String schema_registry){return null;}

}
