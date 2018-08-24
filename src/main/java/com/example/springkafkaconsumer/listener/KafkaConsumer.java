package com.example.springkafkaconsumer.listener;

import com.example.springkafkaconsumer.model.Crime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "crimes", groupId = "group_id", topicPartitions = {
        @TopicPartition(
                topic = "crimes",
                partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")
        )
    })
    public void consume(String message) {
        LOGGER.warn("KafkaConsumer message: " + message);

//        for debug
//        https://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//        Crime crime = mapper.readValue(message, Crime.class);
//
//        LOGGER.warn(crime.toString());
    }

    @KafkaListener(topics = "crimes", groupId = "group_json", containerFactory = "crimeKafkaListenerFactory", topicPartitions = {
            @TopicPartition(
                    topic = "crimes",
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")
            )
    })
    public void crimeJson(Crime crime) {
        LOGGER.warn("KafkaConsumerJson message: " + crime);
    }

}
