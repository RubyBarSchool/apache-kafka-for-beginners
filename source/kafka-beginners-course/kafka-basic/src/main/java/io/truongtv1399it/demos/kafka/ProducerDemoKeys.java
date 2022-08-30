package io.truongtv1399it.demos.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoKeys {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoKeys.class);

    public static void main(String[] args){
        log.info("Demo callback in producer");
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
        for(int i = 0 ; i <= 10; i++){
            String topic = "demo_topic";
            String value = "Demo callback in producer "+i;
            String key = "id";
            // create a producer record
            ProducerRecord<String,String> producerRecord = new ProducerRecord<>(topic,key,value);
            // send the data - asynchronous
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e == null){
                        log.info("Received new metadata/ \n"+
                                "Topic: " + recordMetadata.topic() + "\n"+
                                "Key: " + producerRecord.key() + "\n"+
                                "Partition: " + recordMetadata.partition() + "\n"+
                                "Offset: " + recordMetadata.offset() + "\n"+
                                "Timestamp: " + recordMetadata.timestamp());
                    }else{
                        log.error("Error while producing",e);
                    }
                }
            });

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
        // flush data - synchronous
        producer.flush();
        // flush and close producer
        producer.close();
        // flush and close the Producer
    }
}
