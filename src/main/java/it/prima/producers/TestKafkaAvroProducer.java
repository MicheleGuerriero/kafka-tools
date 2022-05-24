package it.prima.producers;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

public class TestKafkaAvroProducer {
    static String SCHEMA_REGISTRY_URL = "https://psrc-8vyvr.eu-central-1.aws.confluent.cloud";
    static String BOOTSTRAP_SERVERS_URL = "pkc-e8mp5.eu-west-1.aws.confluent.cloud:9092";
    static String TOPIC = "motor-event-save-pre-approval";
    static String schema1 = "{\n" +
            "  \"fields\": [\n" +
            "    {\n" +
            "      \"name\": \"id\",\n" +
            "      \"type\": \"string\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"savedCode\",\n" +
            "      \"type\": \"string\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"approvedAt\",\n" +
            "      \"type\": \"string\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"name\": \"value_motor_event_save_pre_approval\",\n" +
            "  \"type\": \"record\"\n" +
            "}";

    public static void main(String [] args) {
        KafkaProducer kproducer = KafkaAvroProducer.setup(BOOTSTRAP_SERVERS_URL, SCHEMA_REGISTRY_URL);

        Schema.Parser parser = new Schema.Parser();
        Schema schemaMessage = parser.parse(schema1);

        GenericRecord avroRecord = new GenericData.Record(schemaMessage);
        avroRecord.put("id", "Giuseppe");
        avroRecord.put("savedCode", "Verdi");
        avroRecord.put("approvedAt", "yellow");

        String messageKey = "testkey";

        KafkaAvroProducer.publishMessage(kproducer, TOPIC, messageKey, avroRecord);
    }
}