package com.example.unitTests.service;

import com.example.unitTests.model.Cart;
import com.example.unitTests.model.Order;
import com.example.unitTests.model.Product;
import com.example.unitTests.model.WishList;
import com.example.unitTests.repository.CartRepository;
import com.example.unitTests.repository.OrderRepository;
import com.example.unitTests.repository.ProductRepository;
import com.example.unitTests.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final WishListRepository wishListRepository;
    private final OrderRepository orderRepository;

    public void delete() {
        productRepository.deleteAll();
    }

    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    public void removeProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.get();
        Cart cart = product.getCart();
        WishList wishList = product.getWishList();
        Order order = product.getOrder();

        if (cart!=null) {
            cart.getProducts().remove(product);
        }

        if (wishList!=null) {
            wishList.getProducts().remove(product);
        }

        if (order!=null) {
            order.getProducts().remove(product);
        }

        if (cart!=null) {
            cartRepository.save(cart);
        }

        if (wishList!=null) {
            wishListRepository.save(wishList);
        }

        if (order!= null) {
            orderRepository.save(order);
        }

        productRepository.delete(product);
    }
}
