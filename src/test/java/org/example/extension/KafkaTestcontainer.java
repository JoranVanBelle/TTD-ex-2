package org.example.extension;

import static org.example.images.DockerImages.KAFKA_TEST_IMAGE;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;


public class KafkaTestcontainer implements BeforeAllCallback, AfterAllCallback {

    static KafkaContainer kafka;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        kafka = new KafkaContainer(KAFKA_TEST_IMAGE);

        kafka.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        kafka.stop();
    }
}
