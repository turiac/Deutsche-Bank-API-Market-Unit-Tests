package com.example.unitTests.Tests;

import com.example.unitTests.model.Cart;
import com.example.unitTests.model.Order;
import com.example.unitTests.model.Product;
import com.example.unitTests.model.User;
import com.example.unitTests.service.CartService;
import com.example.unitTests.service.OrderService;
import com.example.unitTests.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @Test
    public void whenUserAddsToCartAndPlaceOrder_ThenHaveOrderHistory() {
        User user = new User();
        user.setEmail("test@test.com");
        userService.createUser(user);
        Product newProduct = new Product();
        newProduct.setName("iPhone 13");
        newProduct.setPrice(4199.99);
        newProduct.setQuantity(2);
        cartService.addProduct(user.getId(), newProduct);
        orderService.placeOrder(user.getId());

        List<Cart> carts = (List<Cart>) cartService.getCartById(1L);
        Assertions.assertEquals(1, carts.size());
        Cart cart = carts.get(0);
        Assertions.assertEquals(0, cart.getProducts().size());

        List<Order> orders = orderService.orderHistory(user.getId());
        Assertions.assertEquals(1, orders.size());
        Assertions.assertEquals(1, orders.get(0).getProducts().size());

        Product product = orders.get(0).getProducts().get(0);
        Assertions.assertEquals("iPhone 13", product.getName());
        Assertions.assertEquals(4199.99, product.getPrice());
        Assertions.assertEquals(2, product.getQuantity());
    }
}

