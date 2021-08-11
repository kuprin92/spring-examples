package com.example.demo.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Person {
    @NotNull
    private String name;
    @NotNull
    private String fullName;

    private String className = "Person";
}
