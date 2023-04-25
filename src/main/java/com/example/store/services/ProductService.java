package com.example.store.services;

import com.example.store.models.Product;
import com.example.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        productRepository.save(editedProduct);
    }

    public void setImage(Product product, MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path path = Paths.get("./src/main/resources/static/images/" + fileName);
    //        Path path = Paths.get("C:/Users/bossv/IdeaProjects/Store/src/main/resources/static/images/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setImage(fileName);
    }
}
