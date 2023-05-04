/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk;

import com.brtvsk.dto.PublicationCreateRequestDto;
import com.brtvsk.dto.PublicationUpdateRequestDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.apache.camel.ProducerTemplate;

@Path("/publication")
public class PublicationResource {

    @Inject
    ProducerTemplate producerTemplate;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendMessage(final PublicationCreateRequestDto msg) {
        producerTemplate.sendBody("direct:sendPublicationCreateToKafka", msg);
        return "Message sent to Kafka";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendMessage(final PublicationUpdateRequestDto msg) {
        producerTemplate.sendBody("direct:sendPublicationUpdateToKafka", msg);
        return "Message sent to Kafka";
    }

}
