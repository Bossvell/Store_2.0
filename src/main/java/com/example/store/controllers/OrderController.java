package com.example.store.controllers;

import com.example.store.models.*;
import com.example.store.repositories.CartRepository;
import com.example.store.repositories.OrderProductRepository;
import com.example.store.repositories.OrderRepository;
import com.example.store.repositories.PersonRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final PersonRepository personRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderController(PersonRepository personRepository, CartRepository cartRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.personRepository = personRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @GetMapping("/order/create")
    String createOrder (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> buyer = personRepository.findByLogin(auth.getName());
        Order order = new Order();
        order.setPerson(buyer.get());
        orderRepository.save(order);
        List<OrderProduct> listOrderProduct = new ArrayList<>();
        List<Cart> carts = cartRepository.findByPerson(buyer.get());
        if (carts.size()==0){
            return "/cart";
        }
        for (Cart cart: carts) {
            OrderProduct orderProduct = new OrderProduct();
            Product product = cart.getProduct();
            orderProduct.setOrder(order);
            orderProduct.setName(product.getName());
            orderProduct.setPrice(product.getPrice());
            orderProduct.setProductId(product.getId());
            orderProduct.setQuantity(cart.getQuantity());
            orderProductRepository.save(orderProduct);
            listOrderProduct.add(orderProduct);
            cartRepository.deleteById(cart.getId());
        }
        model.addAttribute("order",order);
        model.addAttribute("listOrderProduct",listOrderProduct);
        return "order";
    }

    @GetMapping("/order/history/{id}")
    public String orderHistory(Model model, @PathVariable("id") int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> buyer = personRepository.findById(id);
        List<Order> orderList = orderRepository.findByPerson(buyer.get());
        model.addAttribute("orderList", orderList);
        return "order_history";
    }

    @GetMapping("/order/{id}")
    public String showOrder(Model model, @PathVariable("id") int id){
        Order order = orderRepository.findById(id).get();
        List<OrderProduct> listOrderProduct = orderProductRepository.findByOrder(order);
        model.addAttribute("order",order);
        model.addAttribute("listOrderProduct",listOrderProduct);
        return "order";
    }

}
