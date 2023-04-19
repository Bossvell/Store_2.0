package com.example.store.repositories;

import com.example.store.models.Order;
import com.example.store.models.OrderProduct;
import com.example.store.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
    List<OrderProduct> findByOrder(Order order);
}
