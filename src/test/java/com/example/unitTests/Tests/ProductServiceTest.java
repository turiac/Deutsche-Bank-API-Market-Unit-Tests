package com.example.unitTests.Tests;

import com.example.unitTests.model.Cart;
import com.example.unitTests.model.Product;
import com.example.unitTests.model.User;
import com.example.unitTests.service.CartService;
import com.example.unitTests.service.ProductService;
import com.example.unitTests.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Test
    public void whenUserAddsProductsToCart_ThenHaveProductsInCart() {
        User user = new User();
        user.setEmail("test@test.com");
        userService.createUser(user);
        Product newProduct = new Product();
        newProduct.setName("iPhone 13");
        newProduct.setPrice(4199.99);
        newProduct.setQuantity(2);
        cartService.addProduct(user.getId(), newProduct);

        List<Cart> carts = (List<Cart>) cartService.getCartById(1L);
        Assertions.assertEquals(1, carts.size());
        Cart cart = carts.get(0);
        Assertions.assertEquals(1, cart.getProducts().size());

        Long id = cart.getProducts().get(0).getId();
        productService.removeProduct(id);
        Product product = productService.getById(id);
        Assertions.assertNull(product);
    }
}
