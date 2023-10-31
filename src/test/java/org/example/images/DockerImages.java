package org.example.images;

import org.testcontainers.utility.DockerImageName;

public interface DockerImages {

    DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:6.2.1");

}
