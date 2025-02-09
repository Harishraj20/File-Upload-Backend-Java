package com.upload.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
    public Product handleFileUpload(
            @RequestParam("productName") String productName,
            @RequestParam("quantity") int quantity,
            @RequestPart("image") MultipartFile file,
            HttpServletRequest request) throws IllegalStateException, IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String uploadDir = "C:/uploads/";
        System.out.println("Upload DIR : " + uploadDir);
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;
        System.out.println("File Seperator: " + File.separator); // Removed this in string concatenation in filePath Hence Correct Path "C:/uploads/1738930259562_iphone.jpg"
        System.out.println("File Name: " + file.getOriginalFilename());
        System.out.println("File Seperator: " + fileName);
        System.out.println("File Path: " + filePath);
        File serverFile = new File(filePath);
        file.transferTo(serverFile);

        Product product = new Product();
        product.setProductName(productName);
        product.setQuantity(quantity);
        product.setImagePath(filePath);
        return product;

        // try {
        // service.addProducts(product);
        // return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message",
        // "Product added Successfully"));

        // } catch (HibernateException e) {
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        // .body(Map.of("message", "Internal Server Error"));

        // } catch (Exception e) {
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        // .body(Map.of("message", "Something went wrong..Try Again Later!"));

        // }
    }

    @GetMapping("/fetchProducts")
    @ResponseBody
    public List<Product> fetchProds() {
        return service.getProducts();
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("C:/uploads/" + filename);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
