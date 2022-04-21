package com.example.unitTests.service;

import com.example.unitTests.model.Cart;
import com.example.unitTests.model.User;
import com.example.unitTests.model.WishList;
import com.example.unitTests.repository.CartRepository;
import com.example.unitTests.repository.UserRepository;
import com.example.unitTests.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final WishListRepository wishListRepository;

    public User createUser(User user) {
        userRepository.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        WishList wishList = new WishList();
        wishList.setUser(user);
        wishListRepository.save(wishList);
        user.setCart(cart);
        user.setWishList(wishList);

        return userRepository.save(user);
    }

    public User getById(Long id) {
        Optional<User> opt = userRepository.findById(id);
        return null;
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
            User user = userOpt.get();
            user.getCart().setUser(null);
            user.getWishList().setUser(null);
            userRepository.delete(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
