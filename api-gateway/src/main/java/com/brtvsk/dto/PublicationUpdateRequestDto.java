/*
 * Copyright myayo.world, Inc 2023-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */

package com.brtvsk.dto;

import com.brtvsk.entity.enums.PublicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationUpdateRequestDto {
    public String id;
    public String city;
    public String title;
    public String description;
    public PublicationStatus status;
}
