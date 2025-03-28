package com.upload.Controller;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import org.hibernate.HibernateException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.upload.Models.Product;
import com.upload.Service.ProductService;

@RunWith(MockitoJUnitRunner.class) //No need to use this if MockitoAnnotations.initMocks(this) is used...;
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMultipartFile file;

    @SuppressWarnings("deprecation")
    @Before
    public void setUp() {
        file = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "dummy image content".getBytes());
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Home Page!"));
    }

    @Test
    public void testHandleFileUpload_Success() throws Exception {
        Product product = new Product();
        when(productService.addProducts(any(Product.class))).thenReturn("Success");
        product.setStockStatus("true");

        mockMvc.perform(multipart("/upload")
                .file(file)
                .param("productName", "Test Product")
                .param("quantity", "10"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Product added successfully")));

        assertEquals("true", product.getStockStatus());

        verify(productService, times(1)).addProducts(any(Product.class));
    }

    @Test
    public void testHandleFileUploadWithEmptyFile_Failure() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", new byte[0]);
    
        mockMvc.perform(multipart("/upload")
                .file(emptyFile)
                .param("productName", "Test Product"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("File is empty")));
    }
    
    @Test
    public void testFetchProducts() throws Exception {
        Product product1 = new Product();
        product1.setProductName("Product 1");

        Product product2 = new Product();
        product2.setProductName("Product 2");

        List<Product> productList = Arrays.asList(product1, product2);

        when(productService.getProducts()).thenReturn(productList);

        mockMvc.perform(get("/fetchProducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName", is("Product 1")))
                .andExpect(jsonPath("$[1].productName", is("Product 2")));

        verify(productService, times(1)).getProducts();
    }

    @Test
    public void handleFileUpload_HibernateException() throws Exception {
        when(productService.addProducts(any(Product.class)))
                .thenThrow(new HibernateException("Database error"));

        mockMvc.perform(multipart("/upload")
                .file(file)
                .param("productName", "Test Product"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Internal Server Error")));
    }

    @Test
    public void handleFileUpload_RunTimeException() throws Exception {
        when(productService.addProducts(any(Product.class)))
                .thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(multipart("/upload")
                .file(file)
                .param("productName", "Test Product"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Something went wrong..Try Again Later!")));
    }

    @Test
    public void testGetImage_Success_MockMvc() throws Exception {
        String filename = "sample.jpg";
        byte[] imageBytes = "fake image data".getBytes();
        Resource mockResource = new ByteArrayResource(imageBytes);
        ResponseEntity<Resource> expectedResponse = ResponseEntity.ok(mockResource);

        when(productService.getImage(filename)).thenReturn(expectedResponse);

        mockMvc.perform(get("/" + filename))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetImage_ServiceThrowsException() throws Exception {
        String filename = "errorFile.jpg";
        when(productService.getImage(filename)).thenThrow(new RuntimeException("Simulated exception"));

        mockMvc.perform(get("/" + filename))
                .andExpect(status().isInternalServerError());
    }
}
