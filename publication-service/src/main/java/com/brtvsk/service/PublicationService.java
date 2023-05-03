package com.brtvsk.service;

import com.brtvsk.dto.PublicationDto;
import com.brtvsk.entity.PublicationEntity;
import com.brtvsk.repo.PublicationRepo;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@ApplicationScoped
public class PublicationService {
    private final PublicationRepo publicationRepo;

    public Uni<List<PublicationDto>> getAll() {
        return publicationRepo.listAll().map(pubs -> pubs.stream()
                .map(pub -> PublicationDto.builder()
                        .id(pub.getId().toHexString())
                        .city(pub.getCity())
                        .title(pub.getTitle())
                        .description(pub.getDescription())
                        .status(pub.getStatus())
                        .createdDate(pub.getCreatedDate())
                        .updatedDate(pub.getUpdatedDate())
                        .build())
                .toList());
    }

    public Uni<List<PublicationDto>> getAllByCity(final String city) {
        return publicationRepo.findByCity(city).map(pubs -> pubs.stream()
                .map(pub -> PublicationDto.builder()
                        .id(pub.getId().toHexString())
                        .city(pub.getCity())
                        .title(pub.getTitle())
                        .description(pub.getDescription())
                        .status(pub.getStatus())
                        .createdDate(pub.getCreatedDate())
                        .updatedDate(pub.getUpdatedDate())
                        .build())
                .toList());
    }

    public Uni<PublicationDto> addPublication(final PublicationDto publicationDto) {
        final PublicationEntity publicationToAdd = PublicationEntity.builder()
                .city(publicationDto.getCity())
                .title(publicationDto.getTitle())
                .status("CREATED")
                .userUUID(publicationDto.getUserUUID())
                .userEmail(publicationDto.getUserEmail())
                .description(publicationDto.getDescription())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        return publicationRepo.persist(publicationToAdd).map(this::mapPublication);
    }

    @Transactional
    public Uni<PublicationDto> updatePublication(final ObjectId bsonId, final PublicationDto publicationDto) {
        return publicationRepo.findByIdOptional(bsonId).chain(optPub -> {
            if (optPub.isPresent()) {
                final PublicationEntity pub = optPub.get();
                Optional.ofNullable(publicationDto.getCity()).ifPresent(pub::setCity);
                Optional.ofNullable(publicationDto.getTitle()).ifPresent(pub::setTitle);
                Optional.ofNullable(publicationDto.getDescription()).ifPresent(pub::setDescription);
                return publicationRepo.persistOrUpdate(pub);
            } else {
                return Uni.createFrom()
                        .failure(() -> new NoSuchElementException(
                                String.format("Not Found entity by id: {%s}", bsonId)));
            }
        }).map(this::mapPublication);
    }

    private PublicationDto mapPublication(final PublicationEntity publicationEntity) {
        return PublicationDto.builder()
                .city(publicationEntity.getCity())
                .title(publicationEntity.getTitle())
                .status(publicationEntity.getStatus())
                .userUUID(publicationEntity.getUserUUID())
                .userEmail(publicationEntity.getUserEmail())
                .description(publicationEntity.getDescription())
                .createdDate(publicationEntity.getCreatedDate())
                .updatedDate(publicationEntity.getUpdatedDate())
                .build();
    }

}
