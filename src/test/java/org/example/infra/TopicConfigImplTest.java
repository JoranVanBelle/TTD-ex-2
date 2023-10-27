package org.example.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
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

            var createdTopic = topicConfig.createTopic("testTopic", 1, 1);

            assertThat(createdTopic.name(), is(equalTo("testTopic")));
            assertThat(createdTopic.numPartitions(), is(equalTo(1)));
            assertThat(createdTopic.replicationFactor(), is(equalTo((short) 1)));
        }

    }

}
