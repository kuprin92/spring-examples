package com.example.demo.controller;

import com.example.demo.dto.PersonDTO;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping("/person")
    public void addPerson(@Validated @RequestBody PersonDTO personDTO) {
        personService.createPerson(personDTO);
    }

    @GetMapping("/person")
    @Validated
    public ResponseEntity<List<PersonDTO>> getPersons() {
        return ResponseEntity.ok(personService.getPersons());
    }
}
