package com.example.unitTests.controller;

import com.example.unitTests.model.Product;
import com.example.unitTests.model.WishList;
import com.example.unitTests.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    private final WishListService wishListService;

    @PutMapping("/create")
    public void createWishList(@RequestBody WishList wishList) {
        wishListService.saveWishList(wishList);
    }

    @GetMapping("/{id}")
    public WishList getWishListById(@PathVariable Long id) {
        return wishListService.getWishListById(id);
    }

    @GetMapping("/price/{id}")
    public void getTotalPrice(@PathVariable Long id) {
        wishListService.getTotalPrice(id);
    }

    @DeleteMapping("/emptyWishList/{id}")
    public void emptyCart(@PathVariable Long id) {
        wishListService.emptyWishList(id);
    }

    @PostMapping("/add/{id}")
    public void addProduct(@PathVariable Long id, @RequestBody Product product) {
        wishListService.addProduct(id, product);
    }
}
