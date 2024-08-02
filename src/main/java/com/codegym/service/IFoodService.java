package com.codegym.service;

import com.codegym.model.Food;

import java.util.List;

public interface IFoodService extends IGenerateService<Food>{

    List<Food> findByShopId(Long shopId);
    List<Food> findByNameContaining(String name);
    List<Food> findByShopIdAndNameContaining(Long shopId, String name);
    List<Food> findByPriceBetween(Double lower, Double higher);


}
