package it.prima.producers;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaJsonProducer {

    private static final String SASL_IAM_JAAS_CONFIG_VALUE = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"3VFXB7T2EWBTQIF2\" password=\"/eMUQZ+fBnUw0k7kbc9DSb7o/mNtf8n4pgDK6DWXybTLtnqzTPixqNwcsouTzF0C\";";

    public static KafkaProducer setup(String BOOTSTRAP_SERVERS_URL, String SCHEMA_REGISTRY_URL) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class);
        props.put("sasl.jaas.config", SASL_IAM_JAAS_CONFIG_VALUE);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "PLAIN");
        props.put("schema.registry.url", SCHEMA_REGISTRY_URL);
        props.put("basic.auth.credentials.source", "USER_INFO");
        props.put("basic.auth.user.info", "T2S3N3YYPTI25Z3Q:r+r2l3vOfZ1LD12xKOhR9YMZ5uh9jzvUkdEugsUcSmv9ENhqPqxbvJnUPugkJ5Mv");

        KafkaProducer producer = new KafkaProducer(props);

        return producer;

    }

    public static void publishMessage(KafkaProducer producer, String topic, String messageKey, SavePreApprovalEvent message) {
        ProducerRecord<String, SavePreApprovalEvent> record = new ProducerRecord<>(topic, messageKey, message);
        try {
            producer.send(record).get();
        } catch(SerializationException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.flush();
            System.out.println("message key published: "+ messageKey);
            System.out.println("message value published: "+ message);
        }
    }
}