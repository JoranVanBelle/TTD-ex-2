package org.example.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TopicConfigImplTest {

    private TopicConfigImpl topicConfig;

    @BeforeEach
    void beforeEach() {
        this.topicConfig = new TopicConfigImpl();
    }

    @Nested
    class WhenParametersAreCorrect {

        @Test
        public void thenTopicIsCreated() {

            assertNotNull(topicConfig.createTopic("topic", 1, (short) 1));

        }

    }

}
