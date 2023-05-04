/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk;

import com.brtvsk.dto.OrderRequestDto;
import com.brtvsk.dto.OrderDto;
import com.brtvsk.model.UserInfo;
import com.brtvsk.service.OrderService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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
@Path("/order")
public class OrderResource {

    private final OrderService orderService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<OrderDto>> getAll(final @RestQuery Optional<String> city) {
        return city.map(orderService::getAllOpenByCity).orElseGet(orderService::getAll);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<OrderDto> createOrder(final OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto, UserInfo.builder().build());
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderDto acceptOrder(final @PathParam("id") ObjectId id, final UserInfo userInfo) {
        return orderService.acceptOrder(id, userInfo);
    }

}
