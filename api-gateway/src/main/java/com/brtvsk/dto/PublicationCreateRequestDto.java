package com.brtvsk.dto;

import com.brtvsk.entity.enums.PublicationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicationCreateRequestDto {
    public String id;
    public String city;
    public String title;
    public String description;
    public PublicationStatus status;
}
