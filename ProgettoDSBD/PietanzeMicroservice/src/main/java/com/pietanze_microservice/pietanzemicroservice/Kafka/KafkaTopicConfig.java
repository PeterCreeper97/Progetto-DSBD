package com.pietanze_microservice.pietanzemicroservice.Kafka;

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

    @Value(value = "${KAFKA_ADDRESS}")
    private String bootstrapAddress;
    @Value(value = "${KAFKA_TOPIC_ORDERS}")
    private String kafkaTopicOrders;
    @Value(value = "${KAFKA_TOPIC_MENU}")
    private String kafkaTopicMenu;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic(kafkaTopicOrders, 100, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(kafkaTopicMenu, 100, (short) 1);
    }

}
