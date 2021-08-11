package com.example.demo.validation;

import lombok.*;

import javax.validation.constraints.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class User {
    @NotNull
    private UUID id;
    @NotBlank
    @Size(min = 3, max = 25)
    private String name;
    @Email
    @NotNull
    private String email;
    @NotNull
    @Min(18)
    @Max(75)
    private int age;
}
