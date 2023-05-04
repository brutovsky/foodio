/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk.mock;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.header.internals.RecordHeaders;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static String USER_EMAIL = "user@gmail.com";
    public static String CUSTOMER_EMAIL = "customer@gmail.com";
    public static String COURIER_EMAIL = "courier@gmail.com";

    public static OutgoingKafkaRecordMetadata<String> mockMsgMetadata(final String email) {
        return OutgoingKafkaRecordMetadata.<String>builder()
                .withHeaders(new RecordHeaders().add("email", email.getBytes()))
                .build();
    }

}
