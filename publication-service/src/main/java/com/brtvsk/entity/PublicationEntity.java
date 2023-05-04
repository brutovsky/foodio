package com.brtvsk.entity;

import com.brtvsk.entity.enums.PublicationStatus;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(database = "publication", collection = "publications")
public class PublicationEntity {
    private ObjectId id; // used by MongoDB for the _id field
    private String city;
    private String title;
    private String description;
    private UUID userUUID;
    private String userEmail;
    private PublicationStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
