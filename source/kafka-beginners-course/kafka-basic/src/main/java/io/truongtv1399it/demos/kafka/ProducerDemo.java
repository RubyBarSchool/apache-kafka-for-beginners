package io.truongtv1399it.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class);

    public static void main(String[] args) {
        log.info("Hello world!");
        // create Producer Properties
        Properties properties = new Properties();
        // template properties
        // properties.setProperty("key","value");
        // server kafka
        // properties.setProperty("bootstrap.server","127.0.0.1:9092");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        // create the Producer
        KafkaProducer<String,String> producer = new KafkaProducer<>(properties);
        // create a producer record
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>("demo_topic","Hello world");
        // send the data - asynchronous
        producer.send(producerRecord);
        // flush data - synchronous
        producer.flush();
        // flush and close producer
        producer.close();
        // flush and close the Producer
    }
}
