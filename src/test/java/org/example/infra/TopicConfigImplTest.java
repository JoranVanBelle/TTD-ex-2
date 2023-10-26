package org.example.infra;

import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaAdmin;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class TopicConfigImplTest {

    private TopicConfigImpl topicConfig;

    @Mock
    private KafkaAdmin admin;

    @BeforeEach
    void beforeEach() {
        this.topicConfig = new TopicConfigImpl();
    }

    @Nested
    class WhenParametersAreCorrect {

        @Test
        public void thenTopicIsCreated() {

            admin.createOrModifyTopics(new NewTopic("topic", 1, (short) 1));

            verify(topicConfig).createTopic("topic", 1, (short) 1);

        }

    }

}
