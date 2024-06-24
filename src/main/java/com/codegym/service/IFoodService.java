package com.codegym.service;

import com.codegym.model.Food;

import java.util.List;

public interface IFoodService extends IGenerateService<Food>{

    List<Food> findByShopId(Long shopId);
}
