package org.example.infra;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;

public class TopicConfigImpl implements TopicConfig {

    public NewTopic createTopic(String topicName, int partions, int replication) {
        return TopicBuilder.name(topicName)
                .partitions(partions)
                .replicas(replication)
                .build();
    }
}
