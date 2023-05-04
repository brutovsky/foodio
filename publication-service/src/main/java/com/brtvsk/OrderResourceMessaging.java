package com.brtvsk;

import com.brtvsk.dto.OrderAcceptDto;
import com.brtvsk.dto.OrderRequestDto;
import com.brtvsk.dto.PublicationCreateRequestDto;
import com.brtvsk.dto.PublicationUpdateRequestDto;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import static com.brtvsk.mock.Utils.COURIER_EMAIL;
import static com.brtvsk.mock.Utils.CUSTOMER_EMAIL;
import static com.brtvsk.mock.Utils.mockMsgMetadata;

@Slf4j
@Path("/order-messaging")
public class OrderResourceMessaging {

    @Channel("order-create-requests-channel")
    Emitter<OrderRequestDto> orderRequestEmitter;

    @Channel("order-accept-requests-channel")
    Emitter<OrderAcceptDto> orderAcceptEmitter;

    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<String> createOrderRequest(final OrderRequestDto orderRequestDto) {
        orderRequestEmitter.send(Message.of(orderRequestDto).addMetadata(mockMsgMetadata(CUSTOMER_EMAIL)));
        log.info("SENDING ORDER CREATE REQUEST");
        return Uni.createFrom().item("Request sent");
    }

    @POST
    @Path("/request-accept")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<String> acceptOrderRequest(final OrderAcceptDto orderAcceptDto) {
        orderAcceptEmitter.send(Message.of(orderAcceptDto).addMetadata(mockMsgMetadata(COURIER_EMAIL)));
        log.info("SENDING ORDER ACCEPT REQUEST");
        return Uni.createFrom().item("Request sent");
    }

}
