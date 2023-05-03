package com.brtvsk.messaging;

import com.brtvsk.dto.PublicationDto;
import com.brtvsk.service.PublicationService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@RequiredArgsConstructor
@Slf4j
@ApplicationScoped
public class PublicationsProcessor {

    private final PublicationService publicationService;

//    @Incoming("requests")
//    @Outgoing("publications")
//    public Uni<PublicationDto> process(final Message<PublicationDto> publicationMessage) {
//        log.info("GOTCHA: " + publicationMessage);
//        return publicationService.addPublication(publicationMessage.getPayload());
//    }


    @Incoming("requests")
    @Outgoing("publications")
    public Uni<PublicationDto> process(final PublicationDto publicationDto) {
        log.info("GOTCHA: " + publicationDto);
        return publicationService.addPublication(publicationDto);
    }

}
