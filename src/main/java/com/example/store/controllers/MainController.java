package com.example.store.controllers;

import com.example.store.models.Person;
import com.example.store.models.Product;
import com.example.store.repositories.ProductRepository;
import com.example.store.services.PersonService;
import com.example.store.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {
    private final PersonValidator personValidator;
    private final PersonService personService;
    private final ProductRepository productRepository;

    public MainController(PersonValidator personValidator, PersonService personService, ProductRepository productRepository) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.productRepository = productRepository;
    }

    @GetMapping("/index")
    String index(Model model){
        List<Product> listProduct = productRepository.findAll();
        model.addAttribute("listProduct", listProduct);
        return "index";
    }
//    @GetMapping("/registration")
//    public String registration(Model model){
//        model.addAttribute("person",new Person());
//        return "registration";
//    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person){
        return "registration";
    }

    @PostMapping("registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){
            return "registration";
        }
        personService.register(person);
        return "redirect:/index";
    }
}
