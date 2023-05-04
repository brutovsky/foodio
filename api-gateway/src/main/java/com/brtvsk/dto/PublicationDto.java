package com.brtvsk.dto;

import com.brtvsk.entity.enums.PublicationStatus;
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
    public PublicationStatus status;
    public LocalDateTime createdDate;
    public LocalDateTime updatedDate;
}
