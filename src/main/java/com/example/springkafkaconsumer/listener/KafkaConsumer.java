package com.example.springkafkaconsumer.listener;

import com.example.springkafkaconsumer.model.Crime;
import com.example.springkafkaconsumer.service.TableStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private
    TableStoreService tableStoreService;

    @KafkaListener(topics = "crimes", groupId = "group_id")
    public void consume(String message) {
        LOGGER.warn("KafkaConsumer message: " + message);
    }

    @KafkaListener(topics = "${kafkaconfig.topic}", id = "${kafkaconfig.groupId}", containerFactory = "crimeKafkaListenerFactory")
    public void crimeJson(List<Crime> crimes) {
        LOGGER.warn("KafkaConsumerJson message size: " + crimes.size());

        tableStoreService.saveMulti(crimes);
    }

}
