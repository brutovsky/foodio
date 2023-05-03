package com.brtvsk.entity;

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
    public ObjectId id; // used by MongoDB for the _id field
    public String city;
    public String title;
    public String description;
    public UUID userUUID;
    public String userEmail;
    public String status;
    public LocalDateTime createdDate;
    public LocalDateTime updatedDate;
}
