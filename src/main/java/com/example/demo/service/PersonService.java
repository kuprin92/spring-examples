package com.example.demo.service;

import com.example.demo.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    void createPerson(PersonDTO personDTO);

    List<PersonDTO> getPersons();
}

