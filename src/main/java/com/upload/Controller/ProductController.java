package com.upload.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.upload.Models.Product;
import com.upload.Service.ProductService;

@Controller
@ResponseBody
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/home")
    public String homePage() {
        return "Hello Home Page!";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> handleFileUpload(
            Product product,
            @RequestParam("image") MultipartFile file,
            HttpServletRequest request) throws IllegalStateException, IOException {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "File is empty"));
        }

        String uploadDir = "C:/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;
        File serverFile = new File(filePath);
        file.transferTo(serverFile);

        product.setImagePath(filePath);

        System.out.println("Received Model Attribute: " + product);

        if (product.getQuantity() > 0) {
            product.setStockStatus("true");
        } else {
            product.setStockStatus("false");
        }
        try {
            service.addProducts(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Product added successfully"));
        } catch (HibernateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Internal Server Error"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Something went wrong..Try Again Later!"));
        }
    }

    @GetMapping("/fetchProducts")
    @ResponseBody
    public List<Product> fetchProds() {

        return service.getProducts();
    }

    @GetMapping("/{filename}")
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            return service.getImage(filename);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/updateProduct/{productId}")
    public void updateProduct(@PathVariable int productId) {
        service.updateProductCount(productId);
    }

    @PostMapping("/updateProduct/icrement/{productId}")
    public void incrementProduct(@PathVariable int productId) {
        service.incrementProdutQuantity(productId);
    }

    @GetMapping("/recommendedproducts")
    public List<Product> recommendProducts() {
        List<Product> productList = service.getProducts();

        List<Product> recommendedProducts = productList.stream()
                .filter(prod -> prod.getRecommendation().equalsIgnoreCase("yes"))
                .collect(Collectors.toList());
        System.out.println(recommendedProducts);

        return recommendedProducts;
    }

    @GetMapping("/search")
    public List<Product> getSearchRecommendations(@RequestParam String val) {
        System.out.println("Value strirng for search: " + val);
        return service.getSearchProducts(val);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getSingleProduct(@PathVariable int id) {
        Product product = service.getProductById(id);

        if (product != null) {
            return ResponseEntity.ok(product);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
    }

}
