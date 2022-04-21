package com.example.unitTests.service;

import com.example.unitTests.model.Cart;
import com.example.unitTests.model.Product;
import com.example.unitTests.model.User;
import com.example.unitTests.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public Cart addProduct(Long id, Product product) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        Cart cart = user.getCart();
        product.setCart(cart);
        productRepository.save(product);
        cart.addProduct(product);
        return cartRepository.save(cart);
    }

    public Cart emptyCart(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        Cart cart = user.getCart();
        List<Long> productToDelete = cart.getProducts().stream().map(product -> product.getId()).toList();
        for (var varId: productToDelete) {
            productService.removeProduct(varId);
        }
        return cartRepository.save(cart);
    }

    public Double getTotalPrice(Long id) {
        return cartRepository.findById(id).get().getPrice();
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }
}
