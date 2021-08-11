package com.example.demo.model;

import com.example.demo.util.Views;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

@RunWith(SpringRunner.class)
@SpringBootTest
class MainModelTest {
    @Test
    public void whenSerializingUsingJsonView_thenCorrect() throws JsonProcessingException {

        MainModel item = new MainModel("Stas", "kuprin", "John");
        String result = new ObjectMapper()
                .writerWithView(Views.Public.class)
                .writeValueAsString(item);

        assertThat(result, containsString("Stas"));
        assertThat(result, containsString("kuprin"));
        assertThat(result, not(containsString("John")));
    }
}