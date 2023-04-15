package com.example.store.controllers;

import com.example.store.models.Product;
import com.example.store.repositories.ProductRepository;
import com.example.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    @Autowired
    public AdminController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
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
    @Transactional
    @PostMapping("/admin/products/add")
    public String addingProduct(@ModelAttribute Product product){
        productRepository.save(product);
        return "redirect:admin_products";
    }


    @GetMapping("/admin/products/{id}")
    public String editProduct(Model model,@PathVariable("id") int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        model.addAttribute("product",optionalProduct.orElse(null));
        return "edit_product";
    }

    @PostMapping("/admin/products/{id}")
    @Transactional
    public String editedProduct(@ModelAttribute Product product){
        productService.editProduct(product);
        return "redirect:/admin/products";
    }

}
