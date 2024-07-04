package com.codegym.service.impl;

import com.codegym.model.Cart;
import com.codegym.repository.CartRepository;
import com.codegym.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;
    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Cart findByUserId(Long id) {
        return cartRepository.findCartByUserId(id);
    }

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public void save(Cart cart) {
           cartRepository.save(cart);
    }

    @Override
    public void remove(Long id) {

    }
}
