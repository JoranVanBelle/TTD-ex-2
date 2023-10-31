package org.example.infra;

import org.example.WeatherRegistered;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class WeatherProducerImpl implements WeatherProducer {

    private static final String topic = "weather";
    private final KafkaTemplate<String, WeatherRegistered> kafkaTemplate;

    public WeatherProducerImpl(KafkaTemplate<String, WeatherRegistered> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRecord(WeatherRegistered weather) {
        kafkaTemplate.send(topic, weather);
    }
}
