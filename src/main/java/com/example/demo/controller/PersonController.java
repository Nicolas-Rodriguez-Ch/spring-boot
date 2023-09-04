package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return personService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.save(person);
        return ResponseEntity.ok(createdPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        if (!personService.getById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        person.setId(id);
        return ResponseEntity.ok(personService.save(person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (!personService.getById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
