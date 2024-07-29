package com.codegym.service.impl;

import com.codegym.model.Cart;
import com.codegym.model.Food;
import com.codegym.model.User;
import com.codegym.repository.CartRepository;
import com.codegym.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        }else{
            Cart rs = cart.get();
            Set<Food> foods = rs.getFood();
            foods.stream().sorted(new Comparator<Food>() {
                @Override
                public int compare(Food o1, Food o2) {
                    return (int) (o1.getShop().getId() - o2.getShop().getId());
                }
            });
            rs.setFood(foods);
            return rs;
        }
    }

    @Override
    public List<Food> getFoodsByCartAndShop(Long user_id,Long shop_id) {
        Optional<Cart> cart = cartRepository.findCartByUserId(user_id);
        Set<Food> foods = new HashSet<>();
        if(cart.isPresent())
        foods = cart.get().getFood();
        List<Food> food_back = new LinkedList<>();
        for(Food food: foods){
            if(food.getShop().getId().equals(shop_id))
                food_back.add(food);
        }
        System.out.println(food_back);
        return  food_back;
    }

    @Override
    public void save(Cart cart) {
           cartRepository.save(cart);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removeAllById(Long id) {
         Cart cart = cartRepository.findCartByUserId(id).get();
         cart.getFood().clear();
         cartRepository.save(cart);
    }
}
