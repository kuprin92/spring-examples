package com.example.demo.model;

import com.example.demo.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class MainModelDTO {
    @JsonView({Views.Public.class, Views.Internal.class})
    private final String className = "MainModelDTO";
    @JsonView({Views.Public.class, Views.Internal.class})
    private String name;
    @JsonView(Views.Public.class)
    private String surname;
    @JsonView(Views.Internal.class)
    private String password;
}
