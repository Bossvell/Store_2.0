package com.example.store.models;

import com.example.store.enums.OrderStatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @ManyToOne
    @JoinColumn(name="person")
    private Person person;

    @Column(name="status")
    private OrderStatusEnum status;

    public Order(){
        this.status = OrderStatusEnum.НОВЫЙ;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
