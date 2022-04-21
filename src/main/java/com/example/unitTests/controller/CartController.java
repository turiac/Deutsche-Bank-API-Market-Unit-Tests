package com.example.unitTests.controller;

import com.example.unitTests.model.Cart;
import com.example.unitTests.model.Product;
import com.example.unitTests.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PutMapping("/create")
    public void createCart(@RequestBody Cart cart) {
        cartService.saveCart(cart);
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @GetMapping("/price/{id}")
    public void getTotalPrice(@PathVariable Long id) {
        cartService.getTotalPrice(id);
    }

    @DeleteMapping("/emptyCart/{id}")
    public void emptyCart(@PathVariable Long id) {
        cartService.emptyCart(id);
    }

    @PutMapping("/add/{id}")
    public void addProduct(@PathVariable Long id, @RequestBody Product product) {
        cartService.addProduct(id, product);
    }
}
