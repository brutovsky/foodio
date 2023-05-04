/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk.messaging;

import com.brtvsk.dto.OrderAcceptDto;
import com.brtvsk.dto.OrderDto;
import com.brtvsk.dto.OrderRequestDto;
import com.brtvsk.model.UserInfo;
import com.brtvsk.service.OrderService;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Slf4j
@ApplicationScoped
public class OrderProcessor {

    private final OrderService orderService;

    @Incoming("order-create-requests")
    public Uni<OrderDto> processCreateOrder(final Message<OrderRequestDto> orderMessage) {
        log.info("GOTCHA: " + orderMessage.getPayload());
        var metadata = orderMessage.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String email = new String(metadata.getHeaders().lastHeader("email").value());
        log.info("EMAIL: " + email);
        OrderRequestDto messagePayload = orderMessage.getPayload();
        return orderService.createOrder(messagePayload, UserInfo.builder()
                        .email(email)
                        .uuid(UUID.randomUUID())
                        .build())
                .onItem().invoke(orderMessage::ack);
    }

    //    @Outgoing("accepted-orders")
    @Blocking
    @Incoming("order-accept-requests")
    public CompletionStage<Void> processAcceptOrder(final Message<OrderAcceptDto> orderAcceptMessage) {
        log.info("GOTCHA: " + orderAcceptMessage.getPayload());
        var metadata = orderAcceptMessage.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String email = new String(metadata.getHeaders().lastHeader("email").value());
        log.info("EMAIL: " + email);
        OrderAcceptDto messagePayload = orderAcceptMessage.getPayload();
        orderService.acceptOrder(messagePayload.getOrderId(), UserInfo.builder()
                .email(email)
                .uuid(UUID.randomUUID())
                .build());
        return orderAcceptMessage.ack();
    }

}
