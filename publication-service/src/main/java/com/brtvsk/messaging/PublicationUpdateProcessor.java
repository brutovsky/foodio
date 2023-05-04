/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk.messaging;

import com.brtvsk.dto.PublicationDto;
import com.brtvsk.dto.PublicationUpdateRequestDto;
import com.brtvsk.service.PublicationService;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Slf4j
@ApplicationScoped
public class PublicationUpdateProcessor {

    private final PublicationService publicationService;

    @Blocking
    @Incoming("publication-update-requests")
    public void processUpdatePublication(final PublicationUpdateRequestDto publicationUpdateRequestDto) {
        log.info("GOTCHA: " + publicationUpdateRequestDto);
        publicationService.updatePublication(publicationUpdateRequestDto.getId(), publicationUpdateRequestDto);
    }

}
