package com.example.demo.service;

import com.example.demo.dto.PersonDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class PersonServiceImplTest {
    @Autowired
    private PersonService personService;

    @Test
    void createPerson() {
        PersonDTO personDTO = PersonDTO.builder().fullName("Kuprin").name("Stas").build();
        personService.createPerson(personDTO);
        PersonDTO personDTO1 = PersonDTO.builder().fullName("Kuprin").name("Stas").build();
        personService.createPerson(personDTO1);
        Assertions.assertEquals(2, personService.getPersons().size());
        Assertions.assertTrue(personService.getPersons().contains(personDTO));
        Assertions.assertTrue(personService.getPersons().contains(personDTO1));


    }

}