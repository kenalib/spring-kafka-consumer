
# Kafka Connect and Spring Kafka Consumer (1)


## Setup Kafka

### Start Kafka servers

```bash
curl -O https://www.apache.org/dyn/closer.cgi?path=/kafka/2.0.0/kafka_2.11-2.0.0.tgz
tar xzf kafka_2.11-2.0.0.tgz
cd kafka_2.11-2.0.0

# zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties
# kafka
bin/kafka-server-start.sh config/server.properties
```

### Create topics

```bash
bin/kafka-topics.sh --zookeeper localhost:2181 \
    --replication-factor 1 --partitions 1 \
    --create --topic test

# check
bin/kafka-topics.sh --zookeeper localhost:2181 --describe
```

### Check if it is working

* Write message

```bash
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
> hello world!
```

* Read message

```bash
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test \
    --from-beginning
```


## Reference

* https://kafka.apache.org/quickstart
