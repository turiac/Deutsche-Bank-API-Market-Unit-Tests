package com.example.unitTests.controller;

import com.example.unitTests.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.removeProduct(id);
    }
}
