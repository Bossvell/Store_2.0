package com.example.store.controllers;

import com.example.store.enums.OrderStatusEnum;
import com.example.store.models.Order;
import com.example.store.models.Person;
import com.example.store.models.Product;
import com.example.store.repositories.OrderRepository;
import com.example.store.repositories.ProductRepository;
import com.example.store.services.PersonService;
import com.example.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final PersonService personService;
    private final OrderRepository orderRepository;

    @Autowired
    public AdminController(ProductRepository productRepository, ProductService productService, PersonService personService, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.personService = personService;
        this.orderRepository = orderRepository;
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
    public String addingProduct(@ModelAttribute Product product, @RequestParam ("file") MultipartFile file){
        productService.setImage(product, file);
        productRepository.save(product);
        return "redirect:/admin/products";
    }
    @GetMapping("/admin/products/{id}")
    public String editProduct(Model model,@PathVariable("id") int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        model.addAttribute("product",optionalProduct.orElse(null));
        return "edit_product";
    }
    @PostMapping("/admin/products/{id}")
    @Transactional
    public String editedProduct(@ModelAttribute Product product, @RequestParam ("file") MultipartFile file) {
        productService.setImage(product, file);
        productService.editProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/{id}/delete")
    public String deleteProduct(@PathVariable("id") int id){
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/users")
    public String adminUsers(Model model)
    {
        List<Person> listPerson = personService.findAllByOrderByIdAsc();
        model.addAttribute("listPerson", listPerson);
        return "admin_users";
    }

    @GetMapping("/admin/users/{id}")
    @Transactional
    public String changeUser(@PathVariable(name = "id") int id){
        Person person = personService.findById(id);
        if(person.getRole().equals("ROLE_ADMIN")){
            personService.findById(person.getId()).setRole("ROLE_USER");
        }else{
            personService.findById(person.getId()).setRole("ROLE_ADMIN");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/orders/{id}")
    public String userOrders(Model model, @PathVariable(name = "id") int id){
        List<Order> orderList = orderRepository.findByPerson(personService.findById(id));
        model.addAttribute("orderList", orderList);
        return "admin_orders";
    }

    @GetMapping("admin/orders")
    public String adminOrders(Model model) {
        List<Order> orderList = orderRepository.findAllByOrderByIdAsc();
        model.addAttribute("orderList", orderList);
        model.addAttribute("order",new Order());
        return "admin_orders";
    }

    @PostMapping("/admin/orders/{id}")
    @Transactional
    public String changeOrderStatus (Model model, @PathVariable(name="id") int id, @ModelAttribute(name="order") Order order){
        orderRepository.findById(id).get().setStatus(order.getStatus());
        List<Order> orderList = orderRepository.findAll();
        model.addAttribute("orderList", orderList);
        return "redirect:/admin/orders";
    }

}
