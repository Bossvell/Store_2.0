package com.example.store.controllers;

import com.example.store.models.Product;
import com.example.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    private final ProductRepository productRepository;
    @Autowired
    public AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/admin/products")
    public String adminProducts(Model model)
    {
        List<Product> listProduct = productRepository.findAll();
        model.addAttribute("listProduct", listProduct);
        return "admin_products";
    }

    @GetMapping("/admin/products/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        return "product_add";
    }

    @PostMapping("/admin/products/add")
    public String addingProduct(@ModelAttribute Product product){
        productRepository.save(product);
        return "redirect:admin_products";
    }

}
