/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk;

import com.brtvsk.dto.OrderAcceptDto;
import com.brtvsk.dto.OrderRequestDto;
import com.brtvsk.dto.PublicationCreateRequestDto;
import com.brtvsk.dto.PublicationUpdateRequestDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.apache.camel.ProducerTemplate;

@Path("/order")
public class OrderResource {

    @Inject
    ProducerTemplate producerTemplate;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendMessage(final OrderRequestDto msg) {
        producerTemplate.sendBody("direct:sendOrderCreateToKafka", msg);
        return "Message sent to Kafka";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendMessage(final OrderAcceptDto msg) {
        producerTemplate.sendBody("direct:sendOrderAcceptToKafka", msg);
        return "Message sent to Kafka";
    }

}
