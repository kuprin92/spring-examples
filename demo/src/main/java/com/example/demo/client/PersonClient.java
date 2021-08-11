package com.example.demo.client;

import com.example.demo.dto.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "person", url = "${persons.url}")
public interface PersonClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/person")
    List<Person> getPersons();


    @RequestMapping(method = RequestMethod.POST, value = "/api/person", consumes = "application/json")
    void savePerson(Person person);
}
