package it.prima.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;

public class TestKafkaJsonProducer {
    static String SCHEMA_REGISTRY_URL = "https://psrc-8vyvr.eu-central-1.aws.confluent.cloud";
    static String BOOTSTRAP_SERVERS_URL = "pkc-e8mp5.eu-west-1.aws.confluent.cloud:9092";
    static String TOPIC = "motor-event-save-pre-approval";

    public static void main(String [] args) throws JsonProcessingException {
        KafkaProducer kproducer = KafkaJsonProducer.setup(BOOTSTRAP_SERVERS_URL, SCHEMA_REGISTRY_URL);


        SavePreApprovalEvent message = new SavePreApprovalEvent("3", "2022-01-18", "2");

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(message));

        String messageKey = "testkey";

        KafkaJsonProducer.publishMessage(kproducer, TOPIC, messageKey, message);

    }
}