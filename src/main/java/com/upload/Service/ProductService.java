package com.upload.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upload.Models.Product;
import com.upload.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String addProducts(Product prod) {
        return productRepository.saveProduct(prod);
    }

    public List<Product> getProducts() {
        return productRepository.fetchProductFromDB();
    }

    public void updateProductCount(int productId) {
        // productRepository.updateCount(productId);
    }

    public void incrementProdutQuantity(int productId) {
        productRepository.incrementCount(productId);
    }

    public List<Map<String, Object>> getSearchProducts(String val) {
        return productRepository.listSearchResults(val);
    }

}
