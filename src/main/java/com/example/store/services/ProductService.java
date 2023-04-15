package com.example.store.services;

import com.example.store.models.Product;
import com.example.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Transactional
    public void editProduct(Product editedProduct) {
        Optional<Product> editedOptionalProduct = productRepository.findById(editedProduct.getId());
        Product product = editedOptionalProduct.orElse(null);
        product.setName(editedProduct.getName());
        product.setDescription(editedProduct.getDescription());
        product.setImage(editedProduct.getImage());
        product.setPrice(editedProduct.getPrice());
        product.setQuantity(editedProduct.getQuantity());
        productRepository.save(product);
    }
}
