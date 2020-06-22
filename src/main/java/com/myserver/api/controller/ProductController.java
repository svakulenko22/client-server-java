package com.myserver.api.controller;

import com.myserver.api.auth.TokenHandler;
import com.myserver.api.dto.SaveProductDTO;
import com.myserver.api.model.Product;
import com.myserver.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private TokenHandler tokenHandler;

    @PostMapping
    public ResponseEntity<Integer> save(@RequestHeader("Authorization") String token, @RequestBody SaveProductDTO productDTO) {
        tokenHandler.parseUserFromToken(token);
        final Integer id = productService.save(productDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestHeader("Authorization") String token, @RequestBody Product product) {
        tokenHandler.parseUserFromToken(token);
        productService.update(product);
        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findOne(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        tokenHandler.parseUserFromToken(token);
        final Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(@RequestHeader("Authorization") String token) {
        tokenHandler.parseUserFromToken(token);
        final List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        tokenHandler.parseUserFromToken(token);
        productService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }
}
