package com.example.store.services;

import com.example.store.models.Person;
import com.example.store.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Person findByLogin(Person person){
        Optional<Person> personDB = personRepository.findByLogin(person.getLogin());
        return personDB.orElse(null);
    }
    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }

    public List<Person> findAll(){
        return(personRepository.findAll());
    }

    public List<Person> findAllByOrderByIdAsc() {return (personRepository.findAllByOrderByIdAsc());}

    public Person findById(int id){
        return personRepository.findById(id).get();
    }
}
