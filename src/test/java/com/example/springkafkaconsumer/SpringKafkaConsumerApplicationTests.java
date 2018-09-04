package com.example.springkafkaconsumer;

import com.example.springkafkaconsumer.config.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringKafkaConsumerApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringKafkaConsumerApplicationTests.class);
	private static final String TOPIC = "test_topic";

	@ClassRule
	public static KafkaEmbedded embeddedKafka =
			new KafkaEmbedded(1, true, TOPIC);

	@Autowired
	private KafkaProperties kafkaProperties;

	@Value("${testdata.crimeTestJson:}")
	private String crimeTestJson;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSimple() throws Exception {
		Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
				"test_group", "false", embeddedKafka);
		DefaultKafkaConsumerFactory<Integer, String> cf =
				new DefaultKafkaConsumerFactory<>(consumerProps);
		ContainerProperties containerProperties = new ContainerProperties(TOPIC);

		KafkaMessageListenerContainer<Integer, String> container;
		container = new KafkaMessageListenerContainer<>(cf, containerProperties);

		final BlockingQueue<ConsumerRecord<Integer, String>> records = new LinkedBlockingQueue<>();

		container.setupMessageListener((MessageListener<Integer, String>) record -> {
			LOGGER.info("RECORD: " + record.toString());
			records.add(record);
		});

		container.setBeanName("templateTests");
		container.start();

		ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
		Map<String, Object> senderProps = KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
		ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);

		KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);

		template.setDefaultTopic(TOPIC);
		template.sendDefault("foo");

		assertThat(records.poll(10, TimeUnit.SECONDS), hasValue("foo"));

		template.sendDefault(0, 2, "bar");
		ConsumerRecord<Integer, String> received = records.poll(10, TimeUnit.SECONDS);

		assertThat(received, hasKey(2));
		assertThat(received, hasPartition(0));
		assertThat(received, hasValue("bar"));

		template.send(TOPIC, 0, 2, "baz");
		received = records.poll(10, TimeUnit.SECONDS);

		assertThat(received, hasKey(2));
		assertThat(received, hasPartition(0));
		assertThat(received, hasValue("baz"));
	}
}
