package com.omkar.E_Comm_Proj.controller;

import com.omkar.E_Comm_Proj.model.Product;
import com.omkar.E_Comm_Proj.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = service.getProductById(id);

        if(product != null) {
            return new ResponseEntity<>(service.getProductById(id),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        try {
            Product product1 = service.addProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageData();
        return ResponseEntity.ok().body(imageFile);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String>updateProduct(@PathVariable int id,@RequestPart Product product,
                                               @RequestPart MultipartFile imageFile) throws IOException {

        Product product1 = null;
        try {
            product1 = service.updateProduct(id,product,imageFile);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
        if(product1!=null) {
            return new ResponseEntity<>("updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product = service.getProductById(id);
        if(product!=null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
        System.out.println("searching with " + keyword);
        List<Product> products = service.searchProduct(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
