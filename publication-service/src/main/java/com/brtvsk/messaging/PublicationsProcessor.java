package com.brtvsk.messaging;

import com.brtvsk.dto.PublicationDto;
import com.brtvsk.dto.PublicationCreateRequestDto;
import com.brtvsk.model.UserInfo;
import com.brtvsk.service.PublicationService;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@ApplicationScoped
public class PublicationsProcessor {

    private final PublicationService publicationService;

    @Incoming("publication-create-requests")
    public Uni<PublicationDto> processCreatePublication(final Message<PublicationCreateRequestDto> publicationMessage) {
        log.info("GOTCHA: " + publicationMessage.getPayload());
        var metadata = publicationMessage.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String email = new String(metadata.getHeaders().lastHeader("email").value());
        log.info("EMAIL: " + email);
        PublicationCreateRequestDto messagePayload = publicationMessage.getPayload();
        return publicationService.addPublication(messagePayload, UserInfo.builder()
                .email(email)
                .uuid(UUID.randomUUID())
                .build());
    }

}
