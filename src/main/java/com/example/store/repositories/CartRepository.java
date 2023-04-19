package com.example.store.repositories;

import com.example.store.models.Cart;
import com.example.store.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByPerson(Person person);
}
