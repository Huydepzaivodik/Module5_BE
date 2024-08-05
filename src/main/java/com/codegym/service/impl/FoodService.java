package com.codegym.service.impl;

import com.codegym.model.Food;
import com.codegym.repository.FoodRepository;
import com.codegym.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService implements IFoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public Food findById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Food food) {
        foodRepository.save(food);
    }

    @Override
    public void remove(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public List<Food> findByShopId(Long shopId) {
        return foodRepository.findByShopId(shopId);
    }

    @Override
    public List<Food> findByNameContaining(String searchName) {
        return foodRepository.findByNameContaining(searchName);
    }

    @Override
    public List<Food> findByShopIdAndNameContaining(Long shopId, String name) {
        return foodRepository.findByShopIdAndNameContaining(shopId, name);
    }

    @Override
    public List<Food> findByPriceBetween(Double lower, Double higher) {
        List<Food> foods = foodRepository.findByPriceBetween(lower, higher);
        return foods;
    }

    @Override
    public List<Food> filterByShopIds(List<Long> shopIds) {
        List<Food> foods = foodRepository.findByShopIds(shopIds);
        return foods;
    }

    @Override
    public Integer findTotalByFoodId(Long food_id) {
        Integer quantity = foodRepository.findTotalByFoodId(food_id);
        return quantity;
    }

    @Override
    public List<Food> findByPriceBetweenAndShopId(Double lower, Double higher, Long shopId) {
        List<Food> foods = foodRepository.findByPriceBetweenAndShopId(lower, higher, shopId);
        return foods;
    }

}
