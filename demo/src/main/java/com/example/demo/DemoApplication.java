package com.example.demo;

import com.example.demo.client.PersonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DemoApplication implements CommandLineRunner {
    @Value("${persons.url}")
    private String personURL;
    @Autowired
    private PersonClient personClient;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
      /*  for (int i = 0; i < 100; i++) {
            personClient.savePerson(PersonDTO.builder().name("Stas" + i).fullName("Kuprin" + i).build());
        }

        List<PersonDTO> persons = personClient.getPersons();
        System.out.println(persons);
*/
    }
}
