package com.example.store.controllers;

import com.example.store.models.Product;
import com.example.store.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FilterController {

    private final ProductRepository productRepository;

    public FilterController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/search")
    public String search(Model model, @ModelAttribute(name="search") String search){
        List<Product> listProduct = productRepository.findAllByNameContaining(search);
        model.addAttribute("listProduct",listProduct);
        return "search";
    }
}
