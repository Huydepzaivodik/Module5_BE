package com.codegym.service.impl;

import com.codegym.model.Cart;
import com.codegym.model.Food;
import com.codegym.model.User;
import com.codegym.repository.CartRepository;
import com.codegym.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;
    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id).get();
    }

    public Cart findByUserId(Long id){
        Optional<Cart> cart = cartRepository.findCartByUserId(id);
        if (cart.isEmpty()){
            Cart newCart = new Cart();
            User user = new User();
            user.setId(id);
            Set<Food> foods = new HashSet<>();
            newCart.setUser(user);
            newCart.setFood(foods);
            return newCart;
        }
        return cart.get();
    }
    @Override
    public void save(Cart cart) {
           cartRepository.save(cart);
    }

    @Override
    public void remove(Long id) {

    }
}
