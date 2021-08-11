package com.example.demo.model;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Person {
    private UUID id;
    private String name;
    private String fullName;
}
