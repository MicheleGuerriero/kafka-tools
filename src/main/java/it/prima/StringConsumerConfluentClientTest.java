
package it.prima;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class StringConsumerConfluentClientTest {


    private static final String SASL_IAM_JAAS_CONFIG_VALUE = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"3VFXB7T2EWBTQIF2\" password=\"/eMUQZ+fBnUw0k7kbc9DSb7o/mNtf8n4pgDK6DWXybTLtnqzTPixqNwcsouTzF0C\";";


    public static void main(String[] args) {
        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", "pkc-e8mp5.eu-west-1.aws.confluent.cloud:9092");
        producerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        producerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        producerProperties.put("sasl.jaas.config", SASL_IAM_JAAS_CONFIG_VALUE);
        producerProperties.put("security.protocol", "SASL_SSL");
        producerProperties.put("sasl.mechanism", "PLAIN");
        producerProperties.put("group.id", "test");
        producerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(producerProperties);
        consumer.subscribe(Collections.singletonList("motor-event-save-pre-approval"));
        while(true) {
            System.out.println("Polling");
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            records.records("motor-event-save-pre-approval").forEach( x -> {
                System.out.println("Key:" + x.key() + " - values:" + x.value());
                System.out.println();
            });
        }

    }
}
