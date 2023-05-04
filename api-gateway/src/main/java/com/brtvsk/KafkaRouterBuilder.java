/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.kafka.common.serialization.StringSerializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class KafkaRouterBuilder extends RouteBuilder {

    @ConfigProperty(name = "kafka.bootstrap.servers")
    String kafkaBootstrapServers;

    @ConfigProperty(name = "kafka.publication.create.topic.name")
    String kafkaPublicationCreateTopicName;

    @ConfigProperty(name = "kafka.publication.update.topic.name")
    String kafkaPublicationUpdateTopicName;

    @ConfigProperty(name = "kafka.order.create.topic.name")
    String kafkaOrderCreateTopicName;

    @ConfigProperty(name = "kafka.order.accept.topic.name")
    String kafkaOrderAcceptTopicName;

    @Override
    public void configure() {
        from("direct:sendPublicationCreateToKafka")
                .log("Message body before serialization: ${body}")
                .setHeader("email", constant("user@example.com"))
                .marshal().json(JsonLibrary.Jackson)
                .log("Message body after serialization: ${body}")
                .toF("kafka:%s?brokers=%s", kafkaPublicationCreateTopicName, kafkaBootstrapServers);

        from("direct:sendPublicationUpdateToKafka")
                .log("Message body before serialization: ${body}")
                .setHeader("email", constant("user@example.com"))
                .marshal().json(JsonLibrary.Jackson)
                .log("Message body after serialization: ${body}")
                .toF("kafka:%s?brokers=%s", kafkaPublicationUpdateTopicName, kafkaBootstrapServers);

        from("direct:sendOrderCreateToKafka")
                .log("Message body before serialization: ${body}")
                .setHeader("email", constant("user@example.com"))
                .marshal().json(JsonLibrary.Jackson)
                .log("Message body after serialization: ${body}")
                .toF("kafka:%s?brokers=%s", kafkaOrderCreateTopicName, kafkaBootstrapServers);

        from("direct:sendOrderAcceptToKafka")
                .log("Message body before serialization: ${body}")
                .setHeader("email", constant("user@example.com"))
                .marshal().json(JsonLibrary.Jackson)
                .log("Message body after serialization: ${body}")
                .toF("kafka:%s?brokers=%s", kafkaOrderAcceptTopicName, kafkaBootstrapServers);
    }
}
