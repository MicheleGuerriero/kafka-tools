
package it.prima;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Future;

public class ProducerClientTest {


    private static final String SASL_IAM_JAAS_CONFIG_VALUE = "software.amazon.msk.auth.iam.IAMLoginModule required awsDebugCreds=true awsProfileName=\"default\";";


    public static void main(String[] args) throws InterruptedException {
        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", "b-3.dl-msk-cluster-staging.ha93zr.c9.kafka.eu-west-1.amazonaws.com:9098,b-1.dl-msk-cluster-staging.ha93zr.c9.kafka.eu-west-1.amazonaws.com:9098,b-2.dl-msk-cluster-staging.ha93zr.c9.kafka.eu-west-1.amazonaws.com:9098");
        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("sasl.jaas.config", SASL_IAM_JAAS_CONFIG_VALUE);
        producerProperties.put("security.protocol", "SASL_SSL");
        producerProperties.put("sasl.mechanism", "AWS_MSK_IAM");
        producerProperties
                .put("sasl.client.callback.handler.class", "software.amazon.msk.auth.iam.IAMClientCallbackHandler");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties);

        Integer j = 0;
        while(true) {
            for (int i=j; i< j + 1; i++) {
                producer.send(new ProducerRecord("motor-event-save-pre-approval", "keys", "{\"savedOn\":\"" + i + "\", \"approvedAt\": \"ciao\", \"id\": \"1\"}"), new Callback() {
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
            Thread.sleep(1);
        }

    }
}
