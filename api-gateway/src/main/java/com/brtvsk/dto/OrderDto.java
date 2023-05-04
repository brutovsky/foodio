/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk.dto;

import com.brtvsk.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String id;
    private String publicationId;
    private String city;
    private String supplierEmail;
    private String customerEmail;
    private String courierEmail;
    private String address;
    private OrderStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
