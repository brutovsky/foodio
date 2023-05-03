package com.brtvsk;

import com.brtvsk.dto.PublicationDto;
import com.brtvsk.service.PublicationService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Path("/publication")
public class PublicationResource {

    private final PublicationService publicationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PublicationDto>> getAll(final @RestQuery Optional<String> city) {
        return city.map(publicationService::getAllByCity).orElseGet(publicationService::getAll);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<PublicationDto> addPublication(final PublicationDto publicationDto) {
        return publicationService.addPublication(publicationDto);
    }

    @Blocking
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<PublicationDto> updatePublication(final @PathParam("id") ObjectId id, final PublicationDto publicationDto) {
        return publicationService.updatePublication(id, publicationDto);
    }

}
