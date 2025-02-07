package com.upload.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upload.Models.Product;
import com.upload.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String addProducts (Product prod){
        return productRepository.saveProduct(prod);
    }

    public List<Product> getProducts() {
       return productRepository.fetchProductFromDB();
    }


}
