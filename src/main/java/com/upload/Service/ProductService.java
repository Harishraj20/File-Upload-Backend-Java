package com.upload.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    public List<Product> getSearchProducts(String val) {
        return productRepository.listSearchResults(val);
    }

    public Product getProductById(int id) {
        return productRepository.fetchProductById(id);
    }

    public ResponseEntity<Resource> getImage(String filename) throws MalformedURLException {
        Path imagePath = Paths.get("C:/uploads/" + filename);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
