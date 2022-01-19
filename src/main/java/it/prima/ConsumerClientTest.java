
package it.prima;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ConsumerClientTest {


    private static final String SASL_IAM_JAAS_CONFIG_VALUE = "software.amazon.msk.auth.iam.IAMLoginModule required;"; //awsProfileName="it";


    public static void main(String[] args) {
        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", "b-3.dl-msk-cluster-staging.ha93zr.c9.kafka.eu-west-1.amazonaws.com:9098,b-1.dl-msk-cluster-staging.ha93zr.c9.kafka.eu-west-1.amazonaws.com:9098,b-2.dl-msk-cluster-staging.ha93zr.c9.kafka.eu-west-1.amazonaws.com:9098");
        producerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        producerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        producerProperties.put("sasl.jaas.config", SASL_IAM_JAAS_CONFIG_VALUE);
        producerProperties.put("security.protocol", "SASL_SSL");
        producerProperties.put("sasl.mechanism", "AWS_MSK_IAM");
        producerProperties.put("group.id", "test");
        producerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        producerProperties
                .put("sasl.client.callback.handler.class", "software.amazon.msk.auth.iam.IAMClientCallbackHandler");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(producerProperties);
        consumer.subscribe(Collections.singletonList("test"));
        while(true) {
            System.out.println("Polling");
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            records.records("test").forEach( x -> {
                System.out.println("Key:" + x.key() + " - values:" + x.value());
                System.out.println();
            });
        }

    }
}
