package com.brtvsk;

import com.brtvsk.dto.PublicationDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Slf4j
@Path("/publication-messaging")
public class PublicationResourceMessaging {

    @Channel("publication-requests")
    Emitter<PublicationDto> publicationRequestEmitter;

    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<String> createAddPublicationRequest(final PublicationDto publicationDto) {
//        publicationRequestEmitter.send(Message.of(publicationDto).withMetadata(Metadata.of("email@gmail.com")));
        publicationRequestEmitter.send(publicationDto);
        log.info("SENDING");
        return Uni.createFrom().item("Request sent");
    }

    @Channel("publications")
    Multi<PublicationDto> publications;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<PublicationDto> stream() {
        return publications;
    }

//    @Route(path = "/*", type = FAILURE)
//    public void error(RoutingContext context) {
//        Throwable t = context.failure();
//        if (t != null) {
//            LOGGER.error("Failed to handle request", t);
//            int status = context.statusCode();
//            String chunk = "";
//            if (t instanceof NoSuchElementException) {
//                status = 404;
//            } else if (t instanceof IllegalArgumentException) {
//                status = 422;
//                chunk = new JsonObject().put("code", status)
//                        .put("exceptionType", t.getClass().getName()).put("error", t.getMessage()).encode();
//            }
//            context.response().setStatusCode(status).end(chunk);
//        } else {
//            // Continue with the default error handler
//            context.next();
//        }
//    }

}
