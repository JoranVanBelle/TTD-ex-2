package org.example.infra;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.example.Person;
import org.example.PersonWeatherJoined;
import org.example.WeatherRegistered;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class PersonWeatherJoin {

    public static Topology buildTopology(
            String user_topic,
            String weather_topic,
            String output_topic,
            Properties props,
            SpecificAvroSerde<Person> personSerde,
            SpecificAvroSerde<WeatherRegistered> weatherSerde
    ) {

        StreamsBuilder builder = new StreamsBuilder();

        var personStream = builder.stream(Collections.singletonList(user_topic),
                Consumed.with(Serdes.String(), personSerde))
                .selectKey((k,v) -> v.getCity());

        var weatherStream = builder.stream(Collections.singletonList(weather_topic),
                Consumed.with(Serdes.String(), weatherSerde))
                .selectKey((k,v) -> v.getLocation());

        personStream.join(weatherStream, PersonWeatherJoin::createJoinedSchema,
                JoinWindows.ofTimeDifferenceAndGrace(Duration.ofMillis(100), Duration.ofMinutes(5)))
                .to(output_topic);

        return builder.build(props);
    }

    private static PersonWeatherJoined createJoinedSchema(Person person, WeatherRegistered weather) {
        return PersonWeatherJoined.newBuilder()
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .setAge(person.getAge())
                .setJob(person.getJob())
                .setCity(person.getCity())
                .setTempC(weather.getTempC())
                .setCondition(weather.getCondition())
                .build();
    }

}
