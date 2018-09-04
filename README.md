
# Kafka Connect and Spring Kafka Consumer


1. [Setup Kafka](README-setup-kafka.md)
2. [Setup Kafka Connect](README-setup-kafka-connect.md)
3. [Try Kafka Connect](#try-kafka-connect)
4. [Spring Kafka Consumer](#spring-kafka-consumer)
5. [Kafka Misc info](README-kafka-misc.md)

-----

## Try Kafka Connect

* Load CSV file

```bash
cp Crimes_-_2001_to_present_head.csv /tmp/spooldir-crimes/input/
```

* check the result

```bash
bin/kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --property print.key=true \
    --topic crimes \
    --from-beginning \
    --max-messages 1 | jq '.'
```

* check the CSV file is moved to /tmp/spooldir-crimes/finished/


## Spring Kafka Consumer

* read data from crimes topic and deserialize JSON
* save the JSON data to NoSQL DB Table Store

```bash
mvn package
java -jar target/spring-kafka-consumer-0.0.1-SNAPSHOT.jar
```


## Reference

* https://kafka.apache.org/documentation/streams/
* https://github.com/kenalib/tablestore-java
* https://www.codenotfound.com/spring-kafka-batch-listener-example.html
* https://docs.spring.io/spring-kafka/docs/current/reference/html/_reference.html#testing
