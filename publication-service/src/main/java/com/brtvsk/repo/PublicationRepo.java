package com.brtvsk.repo;

import com.brtvsk.entity.PublicationEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PublicationRepo implements ReactivePanacheMongoRepository<PublicationEntity> {

    public Uni<List<PublicationEntity>> findByCity(String name) {
        return list("city", name);
    }

}
