/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk.entity;

import com.brtvsk.entity.enums.OrderStatus;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(database = "publication", collection = "orders")
public class OrderEntity {
    private ObjectId id;
    private ObjectId publicationId;
    private String city;
    private String supplierEmail;
    private String customerEmail;
    private String courierEmail;
    private String address;
    private OrderStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
