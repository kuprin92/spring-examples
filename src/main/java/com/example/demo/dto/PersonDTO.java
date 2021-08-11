package com.example.demo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PersonDTO {
    @NotNull
    private String name;
    @NotNull
    private String fullName;
}
