package com.brtvsk.unused;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

@Path("/hello-reactive")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> hello() {
        return Uni.createFrom().item("Hello, World!");
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> stream() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem().transform(n -> "Hello, World! #" + n);
    }
}
