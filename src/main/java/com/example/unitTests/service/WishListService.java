package com.example.unitTests.service;

import com.example.unitTests.model.Product;
import com.example.unitTests.model.User;
import com.example.unitTests.model.WishList;
import com.example.unitTests.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishListRepository wishListRepository;
    private final ProductService productService;

    public WishList addProduct(Long id, Product product) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        WishList wishList = user.getWishList();
        product.setWishList(wishList);
        productRepository.save(product);
        wishList.addProduct(product);
        return wishListRepository.save(wishList);
    }

    public WishList emptyWishList(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        WishList wishList = user.getWishList();
        List<Long> productToDelete = wishList.getProducts().stream().map(product -> product.getId()).toList();
        for (var varId: productToDelete) {
            productService.removeProduct(varId);
        }
        return wishListRepository.save(wishList);
    }

    public Double getTotalPrice(Long id) {
        return wishListRepository.findById(id).get().getPrice();
    }

    public WishList saveWishList(WishList wishList) {
        return wishListRepository.save(wishList);
    }

    public WishList getWishListById(Long id) {
        return wishListRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        wishListRepository.deleteById(id);
    }
}
