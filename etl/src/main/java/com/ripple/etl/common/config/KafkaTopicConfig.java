package com.ripple.etl.common.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.template.default-topic}")
    private String topicName;

    @Value("${kafka.numPartitions}")
    private int numPartitions;

    @Value("${kafka.streams.replication-factor}")
    private short topicReplicationFactor;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin((configs));
    }

    @Bean
    public NewTopic topics() {
        return new NewTopic(
                this.topicName,
                this.numPartitions,
                this.topicReplicationFactor
        );
    }

}
