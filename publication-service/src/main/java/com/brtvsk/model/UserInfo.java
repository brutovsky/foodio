package com.brtvsk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private LocalDate birth;
}
