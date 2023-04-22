package com.example.store.repositories;

import com.example.store.models.Cart;
import com.example.store.models.Order;
import com.example.store.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByPerson(Person person);
    List<Order> findAllByOrderByIdAsc();
}
