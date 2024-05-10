package com.realTimeData.provider.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

;import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public NewTopic topic() {
        if (!topicExists(Constants.AVAILABLE_TIME_SLOTS)) {
            return TopicBuilder
                    .name(Constants.AVAILABLE_TIME_SLOTS)
                    .build();
        }
        System.out.println("Topic already exists " + Constants.AVAILABLE_TIME_SLOTS);
        return null;
    }

    @Bean
    public NewTopic topic2() {
        if (!topicExists(Constants.ACCEPTED_BOOKING)) {
            return TopicBuilder
                    .name(Constants.ACCEPTED_BOOKING)
                    .build();
        }
        System.out.println("Topic already exists " + Constants.ACCEPTED_BOOKING);
        return null;
    }

    private boolean topicExists(String topicName) {
        try (AdminClient adminClient = AdminClient.create(Map.of("bootstrap.servers", bootstrapServers))) {
            return adminClient.listTopics().names().get().contains(topicName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}