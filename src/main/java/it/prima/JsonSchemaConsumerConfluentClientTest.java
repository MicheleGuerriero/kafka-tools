
package it.prima;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class JsonSchemaConsumerConfluentClientTest {


    private static final String SASL_IAM_JAAS_CONFIG_VALUE = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"3VFXB7T2EWBTQIF2\" password=\"/eMUQZ+fBnUw0k7kbc9DSb7o/mNtf8n4pgDK6DWXybTLtnqzTPixqNwcsouTzF0C\";";
    static String SCHEMA_REGISTRY_URL = "https://psrc-8vyvr.eu-central-1.aws.confluent.cloud";


    public static void main(String[] args) {
        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", "pkc-e8mp5.eu-west-1.aws.confluent.cloud:9092");
        producerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        producerProperties.put("value.deserializer", KafkaJsonSchemaDeserializer.class);
        producerProperties.put("sasl.jaas.config", SASL_IAM_JAAS_CONFIG_VALUE);
        producerProperties.put("security.protocol", "SASL_SSL");
        producerProperties.put("sasl.mechanism", "PLAIN");
        producerProperties.put("group.id", "test");
        producerProperties.put("schema.registry.url", SCHEMA_REGISTRY_URL);
        producerProperties.put("basic.auth.credentials.source", "USER_INFO");
        producerProperties.put("basic.auth.user.info", "T2S3N3YYPTI25Z3Q:r+r2l3vOfZ1LD12xKOhR9YMZ5uh9jzvUkdEugsUcSmv9ENhqPqxbvJnUPugkJ5Mv");
        producerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, JsonNode> consumer = new KafkaConsumer<String, JsonNode>(producerProperties);
        consumer.subscribe(Collections.singletonList("motor-event-save-pre-approval"));

        ObjectMapper mapper = new ObjectMapper();

        while(true) {
            System.out.println("Polling");
            ConsumerRecords<String, JsonNode> records = consumer.poll(Duration.ofSeconds(1));
            records.records("motor-event-save-pre-approval").forEach( x -> {
                SavePreApprovalEvent r = mapper.convertValue(x.value(), new TypeReference<SavePreApprovalEvent>(){});
                System.out.println("Key:" + x.key() + " - values:" + r.id);
                System.out.println();
            });
        }

    }
}
