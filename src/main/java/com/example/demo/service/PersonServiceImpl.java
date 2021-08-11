package com.example.demo.service;

import com.example.demo.dto.PersonDTO;
import com.example.demo.model.Person;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private final List<Person> personList = new ArrayList<>();

    @Override
    public void createPerson(PersonDTO personDTO) {
        Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
        personList.add(person);
    }

    @Override
    public List<PersonDTO> getPersons() {
        return personList.stream().map(e -> {
            PersonDTO target = new PersonDTO();
            BeanUtils.copyProperties(e, target);
            return target;
        }).collect(Collectors.toList());
    }
}
