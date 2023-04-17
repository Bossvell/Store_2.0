package com.example.store.controllers;

import com.example.store.models.Product;
import com.example.store.repositories.ProductRepository;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/product/{id}")
    public String showProduct(Model model, @PathVariable("id") int id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) return "redirect:/index";
        model.addAttribute("product", product.orElse(null));
        return "product_cart";
    }

}
