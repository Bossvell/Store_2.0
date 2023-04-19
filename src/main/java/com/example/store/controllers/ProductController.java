package com.example.store.controllers;

import com.example.store.models.Cart;
import com.example.store.models.Person;
import com.example.store.models.Product;
import com.example.store.repositories.CartRepository;
import com.example.store.repositories.PersonRepository;
import com.example.store.repositories.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;

    private final CartRepository cartRepository;

    public ProductController(ProductRepository productRepository, PersonRepository personRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.personRepository = personRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/product/{id}")
    public String showProduct(Model model, @PathVariable("id") int id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) return "redirect:/index";
        model.addAttribute("product", product.orElse(null));
        return "product_cart";
    }

    @PostMapping("/product/addToCard")
    public String addToCard (@ModelAttribute Product product){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> buyer = personRepository.findByLogin(auth.getName());
        Cart cart = new Cart();
        cart.setPerson(buyer.orElse(null));
        cart.setQuantity(product.getQuantity());
        cart.setProduct(productRepository.findById(product.getId()).orElse(null));
        cartRepository.save(cart);
        return "redirect:/product/"+product.getId();
    }

    @GetMapping("/cart")
    public String card(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> buyer = personRepository.findByLogin(auth.getName());
        List<Cart> carts = cartRepository.findByPerson(buyer.orElse(null));
        model.addAttribute("carts", carts);
        return "cart";
    }

    @PostMapping("/cart/delete/{id}")
    public String deleteCard(@PathVariable int id){
        System.out.println(id);
        cartRepository.deleteById(id);
        return "redirect:/cart";
    }
}
