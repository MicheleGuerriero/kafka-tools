
package it.prima;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.time.LocalDateTime;
import java.util.Properties;

public class ProducerConfluentClientTest {


    private static final String SASL_IAM_JAAS_CONFIG_VALUE = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"3VFXB7T2EWBTQIF2\" password=\"/eMUQZ+fBnUw0k7kbc9DSb7o/mNtf8n4pgDK6DWXybTLtnqzTPixqNwcsouTzF0C\";";

    public static void main(String[] args) throws InterruptedException {
        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", "pkc-e8mp5.eu-west-1.aws.confluent.cloud:9092");
        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("sasl.jaas.config", SASL_IAM_JAAS_CONFIG_VALUE);
        producerProperties.put("security.protocol", "SASL_SSL");
        producerProperties.put("sasl.mechanism", "PLAIN");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties);

        Integer j = 0;
        while(true) {
            for (int i=j; i< j + 1; i++) {
                producer.send(new ProducerRecord("motor-event-save-pre-approval", "test-key", "{\"savedOn\":\"" + i + "\", \"approvedAt\": \"ciao\", \"id\": \"1\"}"), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println(LocalDateTime.now() + ": send message " + recordMetadata.offset());
                    }
                });
            }
            producer.flush();
            System.out.println("sent messages from " + j + " to " + (j + 1));
            j = j + 1;
            Thread.sleep(2000);
        }

    }
}
