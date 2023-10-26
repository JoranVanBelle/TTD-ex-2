package org.example.infra;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;

public interface TopicConfig {

    NewTopic createTopic(String topicName, int partions, short replication);

}
