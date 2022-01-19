package it.prima;

import org.apache.kafka.clients.admin.*;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Admin {

    private static final String SASL_IAM_JAAS_CONFIG_VALUE = "software.amazon.msk.auth.iam.IAMLoginModule required;"; //awsProfileName="it"


    public static void main(String[] args) {

        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", "b-3.dl-msk-cluster-staging.ha93zr.c9.kafka.eu-west-1.amazonaws.com:9098,b-1.dl-msk-cluster-staging.ha93zr.c9.kafka.eu-west-1.amazonaws.com:9098,b-2.dl-msk-cluster-staging.ha93zr.c9.kafka.eu-west-1.amazonaws.com:9098");
        // producerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // producerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        producerProperties.put("sasl.jaas.config", SASL_IAM_JAAS_CONFIG_VALUE);
        producerProperties.put("security.protocol", "SASL_SSL");
        producerProperties.put("sasl.mechanism", "AWS_MSK_IAM");
        producerProperties.put("connections.max.idle.ms", 10000);
        producerProperties.put("request.timeout.ms", 5000);
        producerProperties
                .put("sasl.client.callback.handler.class", "software.amazon.msk.auth.iam.IAMClientCallbackHandler");

        NewTopic newTopic = new NewTopic("test", 1, new Integer(3).shortValue());
        AdminClient adminClient = AdminClient.create(producerProperties);
        DescribeTopicsResult descTopic = adminClient.describeTopics(Collections.singletonList("test"));
        try {
            System.out.println(descTopic.all().get());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ListTopicsResult topic = adminClient.listTopics();
        try {
            System.out.println(topic.names().get());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        CreateTopicsResult topics = adminClient.createTopics(Collections.singletonList(newTopic));
        try {
            topics.all().get();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        adminClient.close();
    }
}
