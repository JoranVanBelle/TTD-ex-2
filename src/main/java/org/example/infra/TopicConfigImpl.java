package org.example.infra;

import org.apache.kafka.clients.admin.NewTopic;

public class TopicConfigImpl implements TopicConfig {

    public NewTopic createTopic(String topicName, int partions, short replication) {
        return null;
    }
}
