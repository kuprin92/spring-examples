package com.example.demo.controller;

import com.example.demo.dto.PersonDTO;
import com.example.demo.service.PersonService;
import com.example.demo.service.PersonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = PersonControllerTest.TestContextConfig.class)
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addPerson() throws Exception {
        this.mockMvc.perform(post("/api/person")
                .content(objectMapper.writeValueAsString(PersonDTO.builder().fullName("Nah").name("Wqe").build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(get("/api/person")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(PersonDTO.builder().fullName("Nah").name("Wqe").build()))));
    }
    @Test
    void addPersonFailed() throws Exception {
        this.mockMvc.perform(post("/api/person")
                .content(objectMapper.writeValueAsString(PersonDTO.builder().name("QWERTY").build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isBadRequest());

        this.mockMvc.perform(get("/api/person")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(not(containsString(objectMapper.writeValueAsString(PersonDTO.builder().name("QWERTY").build())))));
    }

    @Test
    void getPersons() throws Exception {
        this.mockMvc.perform(get("/api/person")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(PersonDTO.builder().fullName("Kuprin").name("Stas").build()))))
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(PersonDTO.builder().fullName("Sokolov").name("Ivan").build()))));


    }

    @TestConfiguration
    public static class TestContextConfig {
        @Bean
        public PersonService personService() {
            return new PersonServiceImpl();
        }

        @PostConstruct
        public void init() {
            PersonService personService = personService();
            PersonDTO personDTO = PersonDTO.builder().fullName("Kuprin").name("Stas").build();
            personService.createPerson(personDTO);
            PersonDTO personDTO1 = PersonDTO.builder().fullName("Sokolov").name("Ivan").build();
            personService.createPerson(personDTO1);
            PersonDTO personDTO2 = PersonDTO.builder().fullName(null).name("Ivan").build();
            personService.createPerson(personDTO2);
        }
    }
}