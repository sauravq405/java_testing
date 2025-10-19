package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    private PersonService personService;

    @BeforeEach
    void setUp(){
        PersonRepository personRepository = new PersonRepository();
        personService = new PersonService(personRepository);
    }

    @Test
    void shouldCreatePerson(){
        Person person = personService.create(new Person(null, "Siva", "siva@gmail.com"));
        assertNotNull(person.getId()); //id should not be null post creation
        assertEquals("Siva", person.getName());
        assertEquals("siva@gmail.com", person.getEmail());
    }
}
