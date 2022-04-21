package com.example.unitTests.service;

import com.example.unitTests.model.Cart;
import com.example.unitTests.model.Order;
import com.example.unitTests.model.Product;
import com.example.unitTests.model.User;
import com.example.unitTests.repository.CartRepository;
import com.example.unitTests.repository.OrderRepository;
import com.example.unitTests.repository.ProductRepository;
import com.example.unitTests.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public List<Order> orderHistory(Long id) {
        return orderRepository.findUserIdOrderByDateDesc(id);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    public Order placeOrder(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
            User user = userOptional.get();
            Order newOrder = new Order();
            newOrder.setUser(user);
            orderRepository.save(newOrder);
            userRepository.save(user);

            Cart userCart = user.getCart();
            List<Long> productToOrder = userCart.getProducts().stream().map(prod -> prod.getId()).collect(Collectors.toList());
            userCart.setProducts(new ArrayList<>());
            cartRepository.save(userCart);

            orderRepository.save(newOrder);
                var prodOpt = productRepository.findById(id);
                Product prod = prodOpt.get();
                prod.setCart(null);
                prod.setOrder(newOrder);
                productRepository.save(prod);
                newOrder.getProducts().add(prod);

            return orderRepository.save(newOrder);
        }

}
