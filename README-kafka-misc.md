
# Kafka Connect and Spring Kafka Consumer (5)


## Web UI of Kafka Topic

```bash
git clone https://github.com/jchen86/kafka-topic-viewer
cd kafka-topic-viewer
npm install && npm run start
```

* edit client/src/app/config.js

```
topics: ['test', 'connect-test', 'crimes'],
```

* start app

```bash
cd client
npm install && npm run serve
```


## Tips

### Kafka misc commands

```bash
# show config
bin/kafka-configs.sh --zookeeper localhost:2181 --describe --entity-type topics

# list topics
bin/kafka-topics.sh --zookeeper localhost:2181 --describe
bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic foobar

# show offsets
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe \
    --group connect-local-file-sink

# reset offsets    
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --reset-offsets \
    --to-earliest --all-topics --execute \
    --group connect-local-file-sink
```

### Configs

* replace `INFO` with `WARN` in `config/connect-log4j.properties`

* settings in `config/server.properties`

```bash
# remove log
# https://stackoverflow.com/questions/41429053/how-to-change-consumer-offsets-cleanup-plicy-to-delete-from-compact
log.cleaner.enable=true
# remove topic
delete.topic.enable=true
```

### config to read messages from the beginning

```
@KafkaListener(topics = "crimes", groupId = "group_json", containerFactory = "crimeKafkaListenerFactory", topicPartitions = {
    @TopicPartition(
        topic = "crimes",
        partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")
    )
})
```

### JSON debug snippet

```
// https://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial
ObjectMapper mapper = new ObjectMapper();
mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
Crime crime = mapper.readValue(message, Crime.class);

LOGGER.warn(crime.toString());
```

### show mvn dependency

```bash
mvn dependency:tree -Dincludes=org.springframework.kafka
```

### Another Web UI

* https://github.com/Landoop/kafka-topics-ui
* http://d.hatena.ne.jp/Kazuhira/20180108/1515413171


## Clean up

### Delete topic

* edit `config/server.properties` and restart kafka

```bash
# remove topic
delete.topic.enable=true
```

```bash
bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic foobar
```

### Refresh data

* remove all data and restart servers

```bash
rm -fr /tmp/zookeeper/ /tmp/kafka-logs/ /tmp/connect.offsets
```
