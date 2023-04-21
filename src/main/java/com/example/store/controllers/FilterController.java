package com.example.store.controllers;

import com.example.store.models.Product;
import com.example.store.models.SearchForm;
import com.example.store.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FilterController {

    private final ProductRepository productRepository;
    private final EntityManager em;
    private final List<String> incOrDec = new ArrayList<>();

    public FilterController(ProductRepository productRepository, EntityManager em) {
        this.productRepository = productRepository;
        this.em = em;
        this.incOrDec.add("inc");
        this.incOrDec.add("dec");
    }

    @PostMapping("/search")
    public String search(Model model, @ModelAttribute(name="search") String search){
        List<Product> listProduct = productRepository.findAllByNameContainingIgnoreCase(search);
        int min;
        if(listProduct.size()>0) {
            min = listProduct.get(0).getPrice();
        }else {min = 0;}
        int max = min;
        for (Product prod: listProduct) {
            if (prod.getPrice()<min) min = prod.getPrice();
            if (prod.getPrice()>max) max = prod.getPrice();
        }
        model.addAttribute("listProduct",listProduct);
        SearchForm searchForm = new SearchForm();
        searchForm.setMinPrice(min);
        searchForm.setMaxPrice(max);
        model.addAttribute("incOrDec",incOrDec);
        model.addAttribute("searchForm",searchForm);
        return "search";
    }

    @PostMapping("/proSearch")
    public String proSearch(Model model, @ModelAttribute(name="searchForm") SearchForm searchForm){
        System.out.println(searchForm.toQuery());
        Query query = em.createQuery(searchForm.toQuery());
        List<Product> listProduct = query.getResultList();
        model.addAttribute("listProduct",listProduct);
        model.addAttribute("incOrDec",incOrDec);
        model.addAttribute("searchForm",searchForm);
        return "search";
    }
}
