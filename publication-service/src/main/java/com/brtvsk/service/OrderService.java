/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk.service;

import com.brtvsk.dto.OrderRequestDto;
import com.brtvsk.dto.OrderDto;
import com.brtvsk.entity.OrderEntity;
import com.brtvsk.entity.enums.OrderStatus;
import com.brtvsk.model.UserInfo;
import com.brtvsk.repo.OrderRepo;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@ApplicationScoped
public class OrderService {

    private final OrderRepo orderRepo;

    public Uni<List<OrderDto>> getAllOpenByCity(final String city) {
        return orderRepo.findOpenOrdersByCity(city).map(pubs -> pubs.stream()
                .map(this::mapOrder).toList());
    }

    public Uni<List<OrderDto>> getAll() {
        return orderRepo.listAll().map(pubs -> pubs.stream().map(this::mapOrder).toList());
    }

    public Uni<OrderDto> createOrder(final OrderRequestDto orderRequestDto, final UserInfo userInfo) {
        final OrderEntity order = OrderEntity.builder()
                .city(orderRequestDto.getCity())
                .publicationId(orderRequestDto.getPublicationId())
                .status(OrderStatus.OPEN)
                .address(orderRequestDto.getAddress())
                .customerEmail(userInfo.getEmail())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        return orderRepo.persist(order).map(this::mapOrder);
    }

    @Transactional
    public OrderDto acceptOrder(final ObjectId bsonId, final UserInfo courierInfo) {
        return orderRepo.findByIdOptional(bsonId).chain(orderOpt -> {
            if (orderOpt.isPresent()) {
                final OrderEntity order = orderOpt.get();
                order.setStatus(OrderStatus.ACCEPTED);
                order.setCourierEmail(courierInfo.getEmail());
                return orderRepo.update(order);
            } else {
                return Uni.createFrom()
                        .failure(() -> new NoSuchElementException(
                                String.format("Not Found entity by id: {%s}", bsonId)));
            }
        }).map(this::mapOrder).await().indefinitely();
    }

//    public Uni<List<OrderDto>> closeOrder(final String city) {
//        return orderRepo.findOpenOrdersByCity(city).map(pubs -> pubs.stream()
//                .map(pub -> OrderDto.builder()
//                        .build())
//                .toList());
//    }

    private OrderDto mapOrder(final OrderEntity orderEntity) {
        return OrderDto.builder()
                .id(orderEntity.getId())
                .publicationId(orderEntity.getPublicationId())
                .city(orderEntity.getCity())
                .address(orderEntity.getAddress())
                .status(orderEntity.getStatus())
                .supplierEmail(orderEntity.getSupplierEmail())
                .customerEmail(orderEntity.getCustomerEmail())
                .courierEmail(orderEntity.getCourierEmail())
                .createdDate(orderEntity.getCreatedDate())
                .updatedDate(orderEntity.getUpdatedDate())
                .build();
    }

}
