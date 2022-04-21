package com.example.unitTests.Tests;

import com.example.unitTests.model.Cart;
import com.example.unitTests.model.Product;
import com.example.unitTests.model.User;
import com.example.unitTests.service.CartService;
import com.example.unitTests.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @Test
    public void whenUserAddsProducts_ThenAddProductsInCart() {
        User user = new User();
        user.setEmail("test@test.com");
        userService.createUser(user);
        Product newProduct = new Product();
        newProduct.setName("iPhone 13");
        newProduct.setPrice(4199.99);
        newProduct.setQuantity(2);
        cartService.addProduct(user.getId(), newProduct);
        List<Cart> carts = (List<Cart>) cartService.getCartById(0L);
        Assertions.assertEquals(1, carts.size());

        Cart cart = carts.get(0);
        Assertions.assertEquals(1, cart.getProducts().size());

        Product product = cart.getProducts().get(0);
        Assertions.assertEquals("iPhone 13", product.getName());
        Assertions.assertEquals(4199.99, product.getPrice());
        Assertions.assertEquals(2, product.getQuantity());
    }
}