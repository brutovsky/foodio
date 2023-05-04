/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk.repo;

import com.brtvsk.entity.OrderEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

import static com.brtvsk.entity.enums.OrderStatus.OPEN;

@ApplicationScoped
public class OrderRepo implements ReactivePanacheMongoRepository<OrderEntity> {

    public Uni<List<OrderEntity>> findOpenOrdersByCity(String city) {
        return list("city = ?1 and status = ?2", city, OPEN);
    }

}
