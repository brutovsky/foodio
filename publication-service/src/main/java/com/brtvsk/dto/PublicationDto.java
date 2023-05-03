package com.brtvsk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDto {
    public String id;
    public UUID userUUID;
    public String city;
    public String title;
    public String description;
    public String userEmail;
    public String status;
    public LocalDateTime createdDate;
    public LocalDateTime updatedDate;
}
