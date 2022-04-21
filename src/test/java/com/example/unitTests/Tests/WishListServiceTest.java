package com.example.unitTests.Tests;

import com.example.unitTests.model.Product;
import com.example.unitTests.model.User;
import com.example.unitTests.model.WishList;
import com.example.unitTests.service.UserService;
import com.example.unitTests.service.WishListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WishListServiceTest {
    @Autowired
    private WishListService wishlistService;
    @Autowired
    private UserService userService;

    @Test
    public void whenUserAddsProductsToWishlist_ThenAddProductsInWishlist() {
        User user = new User();
        user.setEmail("test@test.com");
        userService.createUser(user);
        Product newProduct = new Product();
        newProduct.setName("iPhone 13");
        newProduct.setPrice(4199.99);
        newProduct.setQuantity(2);
        wishlistService.addProduct(user.getId(), newProduct);

        List<WishList> wishlists = (List<WishList>) wishlistService.getWishListById(1L);
        Assertions.assertEquals(1, wishlists.size());
        WishList wishlist = wishlists.get(0);
        Assertions.assertEquals(1, wishlist.getProducts().size());

        Product product = wishlist.getProducts().get(0);
        Assertions.assertEquals("iPhone 13", product.getName());
        Assertions.assertEquals(4199.99, product.getPrice());
        Assertions.assertEquals(2, product.getQuantity());
    }
}
