package com.codegym.service;

import com.codegym.model.Food;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

public interface IFoodService extends IGenerateService<Food>{

    List<Food> findByShopId(Long shopId);
    List<Food> findByNameContaining(String name);
    List<Food> findByShopIdAndNameContaining(Long shopId, String name);
    List<Food> findByPriceBetween(Double lower, Double higher);
    List<Food> findByPriceBetweenAndShopId(Double lower, Double higher, Long shopId);
    List<Food> filterByShopIds(List<Long> shopIds);
    Integer findTotalByFoodId(Long food_id);
    HashMap<String,List<Food>> findSimilarFoodsByFoodId(Long id);

    List<Food> getBestSellerFoods();
}
