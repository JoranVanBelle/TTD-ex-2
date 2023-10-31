package org.example.infra;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.example.KafkaTopologyTestBase;
import org.example.Person;
import org.example.PersonWeatherJoined;
import org.example.WeatherRegistered;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;

import static org.example.configuration.SerdeFactory.personSerde;
import static org.example.configuration.SerdeFactory.personWeatherSerde;
import static org.example.configuration.SerdeFactory.weatherSerde;
import static org.example.infra.PersonWeatherJoin.buildTopology;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PersonWeatherJoinTest extends KafkaTopologyTestBase {

    private static final String PERSON_TOPIC = "person";
    private static final String WEATHER_TOPIC = "weather";
    private static final String OUTPUT_TOPIC = "output";

    TestInputTopic<String, Person> userTopic;
    TestInputTopic<String, WeatherRegistered> weatherTopic;
    TestOutputTopic<String, PersonWeatherJoined> outputTopic;

    @BeforeEach
    void beforeEach() {
        this.testDriver = createTestDriver(
            buildTopology(
                    PERSON_TOPIC,
                    WEATHER_TOPIC,
                    OUTPUT_TOPIC,
                    serdesConfigTest(),
                    personSerde(MOCK_SR),
                    weatherSerde(MOCK_SR)
            ));
        userTopic = testDriver.createInputTopic(PERSON_TOPIC, new StringSerializer(), personSerde(MOCK_SR).serializer());
        weatherTopic = testDriver.createInputTopic(WEATHER_TOPIC, new StringSerializer(), weatherSerde(MOCK_SR).serializer());
        outputTopic = testDriver.createOutputTopic(OUTPUT_TOPIC, new StringDeserializer(), personWeatherSerde(MOCK_SR).deserializer());
    }

    @AfterEach
    void afterEach() {
        this.testDriver.close();
    }

    @Nested
    class WhenNothingIsWrong {

        @Test
        void thenEverythingCanBeJoined_firstPersonsThenWeather_sixItemsInOutput() {
            userTopic.pipeInput("p1", Person.newBuilder().setFirstName("f1").setLastName("l1").setAge(1).setJob("j1").setCity("c1").build());
            userTopic.pipeInput("p2", Person.newBuilder().setFirstName("f2").setLastName("l2").setAge(2).setJob("j2").setCity("c2").build());
            userTopic.pipeInput("p3", Person.newBuilder().setFirstName("f3").setLastName("l3").setAge(3).setJob("j3").setCity("c3").build());
            userTopic.pipeInput("p4", Person.newBuilder().setFirstName("f4").setLastName("l4").setAge(4).setJob("j4").setCity("c1").build());
            userTopic.pipeInput("p5", Person.newBuilder().setFirstName("f5").setLastName("l5").setAge(5).setJob("j5").setCity("c2").build());
            userTopic.pipeInput("p6", Person.newBuilder().setFirstName("f6").setLastName("l6").setAge(6).setJob("j6").setCity("c3").build());

            weatherTopic.pipeInput("w1", WeatherRegistered.newBuilder().setLocation("c1").setTempC(1).setCondition("con1").build());
            weatherTopic.pipeInput("w2", WeatherRegistered.newBuilder().setLocation("c2").setTempC(1).setCondition("con2").build());
            weatherTopic.pipeInput("w3", WeatherRegistered.newBuilder().setLocation("c3").setTempC(1).setCondition("con3").build());
            weatherTopic.pipeInput("w4", WeatherRegistered.newBuilder().setLocation("c4").setTempC(1).setCondition("con4").build());

            var resultList = outputTopic.readRecordsToList();

            assertThat(resultList.size(), is(equalTo(6)));
        }

        @Test
        void thenEverythingCanBeJoined_firstPersonsThenWeather_contentFirstRecordOk() {
            userTopic.pipeInput("p1", Person.newBuilder().setFirstName("f1").setLastName("l1").setAge(1).setJob("j1").setCity("c1").build());
            userTopic.pipeInput("p2", Person.newBuilder().setFirstName("f2").setLastName("l2").setAge(2).setJob("j2").setCity("c2").build());
            userTopic.pipeInput("p3", Person.newBuilder().setFirstName("f3").setLastName("l3").setAge(3).setJob("j3").setCity("c3").build());
            userTopic.pipeInput("p4", Person.newBuilder().setFirstName("f4").setLastName("l4").setAge(4).setJob("j4").setCity("c1").build());
            userTopic.pipeInput("p5", Person.newBuilder().setFirstName("f5").setLastName("l5").setAge(5).setJob("j5").setCity("c2").build());
            userTopic.pipeInput("p6", Person.newBuilder().setFirstName("f6").setLastName("l6").setAge(6).setJob("j6").setCity("c3").build());

            weatherTopic.pipeInput("w1", WeatherRegistered.newBuilder().setLocation("c1").setTempC(1).setCondition("con1").build());
            weatherTopic.pipeInput("w2", WeatherRegistered.newBuilder().setLocation("c2").setTempC(2).setCondition("con2").build());
            weatherTopic.pipeInput("w3", WeatherRegistered.newBuilder().setLocation("c3").setTempC(3).setCondition("con3").build());
            weatherTopic.pipeInput("w4", WeatherRegistered.newBuilder().setLocation("c4").setTempC(4).setCondition("con4").build());

            var firstPersonWeatherJoined = outputTopic.readRecordsToList().get(0).value();

            assertThat(firstPersonWeatherJoined, is(equalTo(expectedFirstPersonWeatherJoined_p1())));
        }

        @Test
        void thenEverythingCanBeJoined_firstWeatherThenPerson_contentFirstRecordOk() {
            weatherTopic.pipeInput("w1", WeatherRegistered.newBuilder().setLocation("c1").setTempC(1).setCondition("con1").build());
            weatherTopic.pipeInput("w2", WeatherRegistered.newBuilder().setLocation("c2").setTempC(2).setCondition("con2").build());
            weatherTopic.pipeInput("w3", WeatherRegistered.newBuilder().setLocation("c3").setTempC(3).setCondition("con3").build());
            weatherTopic.pipeInput("w4", WeatherRegistered.newBuilder().setLocation("c4").setTempC(4).setCondition("con4").build());

            userTopic.pipeInput("p1", Person.newBuilder().setFirstName("f1").setLastName("l1").setAge(1).setJob("j1").setCity("c1").build());
            userTopic.pipeInput("p2", Person.newBuilder().setFirstName("f2").setLastName("l2").setAge(2).setJob("j2").setCity("c2").build());
            userTopic.pipeInput("p3", Person.newBuilder().setFirstName("f3").setLastName("l3").setAge(3).setJob("j3").setCity("c3").build());
            userTopic.pipeInput("p4", Person.newBuilder().setFirstName("f4").setLastName("l4").setAge(4).setJob("j4").setCity("c1").build());
            userTopic.pipeInput("p5", Person.newBuilder().setFirstName("f5").setLastName("l5").setAge(5).setJob("j5").setCity("c2").build());
            userTopic.pipeInput("p6", Person.newBuilder().setFirstName("f6").setLastName("l6").setAge(6).setJob("j6").setCity("c3").build());

            var firstPersonWeatherJoined = outputTopic.readRecordsToList().get(0).value();

            assertThat(firstPersonWeatherJoined, is(equalTo(expectedFirstPersonWeatherJoined_p1())));
        }

        @Test
        void thenEverythingCanBeJoined_sixItemsInOutput() {
            weatherTopic.pipeInput("w1", WeatherRegistered.newBuilder().setLocation("c1").setTempC(1).setCondition("con1").build());
            weatherTopic.pipeInput("w2", WeatherRegistered.newBuilder().setLocation("c2").setTempC(2).setCondition("con2").build());
            weatherTopic.pipeInput("w3", WeatherRegistered.newBuilder().setLocation("c3").setTempC(3).setCondition("con3").build());
            weatherTopic.pipeInput("w4", WeatherRegistered.newBuilder().setLocation("c4").setTempC(4).setCondition("con4").build());

            userTopic.pipeInput("p1", Person.newBuilder().setFirstName("f1").setLastName("l1").setAge(1).setJob("j1").setCity("c1").build());
            userTopic.pipeInput("p2", Person.newBuilder().setFirstName("f2").setLastName("l2").setAge(2).setJob("j2").setCity("c2").build());
            userTopic.pipeInput("p3", Person.newBuilder().setFirstName("f3").setLastName("l3").setAge(3).setJob("j3").setCity("c3").build());
            userTopic.pipeInput("p4", Person.newBuilder().setFirstName("f4").setLastName("l4").setAge(4).setJob("j4").setCity("c1").build());
            userTopic.pipeInput("p5", Person.newBuilder().setFirstName("f5").setLastName("l5").setAge(5).setJob("j5").setCity("c2").build());
            userTopic.pipeInput("p6", Person.newBuilder().setFirstName("f6").setLastName("l6").setAge(6).setJob("j6").setCity("c3").build());

            var resultList = outputTopic.readRecordsToList();

            assertThat(resultList.size(), is(equalTo(6)));
        }

        @Test
        void thenEverythingCanBeJoined_randomPersonAndWeather_contentFirstRecordOk() {

            userTopic.pipeInput("p1", Person.newBuilder().setFirstName("f1").setLastName("l1").setAge(1).setJob("j1").setCity("c1").build());

            weatherTopic.pipeInput("w2", WeatherRegistered.newBuilder().setLocation("c2").setTempC(2).setCondition("con2").build());

            userTopic.pipeInput("p2", Person.newBuilder().setFirstName("f2").setLastName("l2").setAge(2).setJob("j2").setCity("c2").build());

            weatherTopic.pipeInput("w1", WeatherRegistered.newBuilder().setLocation("c1").setTempC(1).setCondition("con1").build());

            userTopic.pipeInput("p4", Person.newBuilder().setFirstName("f4").setLastName("l4").setAge(4).setJob("j4").setCity("c1").build());
            userTopic.pipeInput("p3", Person.newBuilder().setFirstName("f3").setLastName("l3").setAge(3).setJob("j3").setCity("c3").build());

            weatherTopic.pipeInput("w3", WeatherRegistered.newBuilder().setLocation("c3").setTempC(3).setCondition("con3").build());

            userTopic.pipeInput("p5", Person.newBuilder().setFirstName("f5").setLastName("l5").setAge(5).setJob("j5").setCity("c2").build());

            weatherTopic.pipeInput("w4", WeatherRegistered.newBuilder().setLocation("c4").setTempC(4).setCondition("con4").build());

            userTopic.pipeInput("p6", Person.newBuilder().setFirstName("f6").setLastName("l6").setAge(6).setJob("j6").setCity("c3").build());

            var firstPersonWeatherJoined = outputTopic.readRecordsToList().get(0).value();

            assertThat(firstPersonWeatherJoined, is(equalTo(expectedFirstPersonWeatherJoined_p2())));
        }

        @Test
        void thenEverythingCanBeJoined_randomPersonAndWeather_sixItemsInOutput() {

            userTopic.pipeInput("p1", Person.newBuilder().setFirstName("f1").setLastName("l1").setAge(1).setJob("j1").setCity("c1").build());

            weatherTopic.pipeInput("w2", WeatherRegistered.newBuilder().setLocation("c2").setTempC(2).setCondition("con2").build());

            userTopic.pipeInput("p2", Person.newBuilder().setFirstName("f2").setLastName("l2").setAge(2).setJob("j2").setCity("c2").build());

            weatherTopic.pipeInput("w1", WeatherRegistered.newBuilder().setLocation("c1").setTempC(1).setCondition("con1").build());

            userTopic.pipeInput("p4", Person.newBuilder().setFirstName("f4").setLastName("l4").setAge(4).setJob("j4").setCity("c1").build());
            userTopic.pipeInput("p3", Person.newBuilder().setFirstName("f3").setLastName("l3").setAge(3).setJob("j3").setCity("c3").build());

            weatherTopic.pipeInput("w3", WeatherRegistered.newBuilder().setLocation("c3").setTempC(3).setCondition("con3").build());

            userTopic.pipeInput("p5", Person.newBuilder().setFirstName("f5").setLastName("l5").setAge(5).setJob("j5").setCity("c2").build());

            weatherTopic.pipeInput("w4", WeatherRegistered.newBuilder().setLocation("c4").setTempC(4).setCondition("con4").build());

            userTopic.pipeInput("p6", Person.newBuilder().setFirstName("f6").setLastName("l6").setAge(6).setJob("j6").setCity("c3").build());

            var resultList = outputTopic.readRecordsToList();

            assertThat(resultList.size(), is(equalTo(6)));
        }

        private PersonWeatherJoined expectedFirstPersonWeatherJoined_p1() {
            return PersonWeatherJoined.newBuilder()
                    .setFirstName("f1")
                    .setLastName("l1")
                    .setAge(1)
                    .setJob("j1")
                    .setCity("c1")
                    .setTempC(1)
                    .setCondition("con1")
                    .build();
        }

        private PersonWeatherJoined expectedFirstPersonWeatherJoined_p2() {
            return PersonWeatherJoined.newBuilder()
                    .setFirstName("f2")
                    .setLastName("l2")
                    .setAge(2)
                    .setJob("j2")
                    .setCity("c2")
                    .setTempC(2)
                    .setCondition("con2")
                    .build();
        }

    }

    @Nested
    class WhenSomethingIsWrong {

        @Test
        void thenNotEverythingWillBeInOutput_notAllWeatherExists() {

            userTopic.pipeInput("p1", Person.newBuilder().setFirstName("f1").setLastName("l1").setAge(1).setJob("j1").setCity("c1").build());
            userTopic.pipeInput("p2", Person.newBuilder().setFirstName("f2").setLastName("l2").setAge(2).setJob("j2").setCity("c2").build());
            userTopic.pipeInput("p3", Person.newBuilder().setFirstName("f3").setLastName("l3").setAge(3).setJob("j3").setCity("c3").build());
            userTopic.pipeInput("p4", Person.newBuilder().setFirstName("f4").setLastName("l4").setAge(4).setJob("j4").setCity("c1").build());
            userTopic.pipeInput("p5", Person.newBuilder().setFirstName("f5").setLastName("l5").setAge(5).setJob("j5").setCity("c2").build());
            userTopic.pipeInput("p6", Person.newBuilder().setFirstName("f6").setLastName("l6").setAge(6).setJob("j6").setCity("c3").build());

            weatherTopic.pipeInput("w1", WeatherRegistered.newBuilder().setLocation("c1").setTempC(1).setCondition("con1").build());
            weatherTopic.pipeInput("w2", WeatherRegistered.newBuilder().setLocation("c2").setTempC(2).setCondition("con2").build());
            weatherTopic.pipeInput("w4", WeatherRegistered.newBuilder().setLocation("c4").setTempC(4).setCondition("con4").build());

            var resultList = outputTopic.readRecordsToList();

            assertThat(resultList.size(), is(equalTo(4)));
        }

    }

}
