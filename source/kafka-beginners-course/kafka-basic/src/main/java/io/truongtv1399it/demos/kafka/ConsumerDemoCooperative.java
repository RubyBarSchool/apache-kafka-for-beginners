package io.truongtv1399it.demos.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoWithShutDown {

    private static final Logger log = LoggerFactory.getLogger(ConsumerDemoWithShutDown.class);

    public static void main(String[] args) {
        log.info("I am a Kafka Consumer");
        
        String bootstrapServer = "127.0.0.1:9092";
        String groupId = "my-second-application";
        String autoOffset = "none";
        // none: throw exception to the consumer if no previous offset is found for the consumer's group.
        // earliest: automatically reset the offset to the earliest offset.
        // latest: automatically reset the offset to the latest offset.
        // anything else: throw exception to the consumer.
        String topic = "demo_topic";

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,autoOffset);

        //create consumer
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);

        //get a reference to the current thread
        final Thread mainThread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                log.info("Detected a shutdown, Let's exit calling consumer.wakeup()....");
                consumer.wakeup();

                // join the main thread to allow the execution of the code in the main thread
                try{
                    mainThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        try{
            // subscribe consumer to our topics(s)
            consumer.subscribe(Arrays.asList(topic));

            // poll for new data
            while (true){
                log.info("Polling");

                ConsumerRecords<String, String> consumerRecord = consumer.poll(Duration.ofMillis(1000));

                for(ConsumerRecord<String,String> record : consumerRecord){
                    log.info("Key: "+record.key() + ", Value: "+record.value());
                    log.info("Partition: "+ record.partition()+", Offset: "+record.offset());
                }
            }
        } catch (WakeupException e){
            log.info("Wake up exception!");
            // we ignore this as this is an expected exception when closing a consumer
        } catch (Exception e){
            log.error("Unexpected exception");
        } finally {
            consumer.close(); // this will also commit the offsets if need be
            log.info("The consumer is now gracefully closed");
        }


    }
}
