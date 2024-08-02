package com.codegym.repository;

import com.codegym.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {
    List<Food> findByShopId(Long shopId);

    List<Food> findByNameContaining(String name);
    List<Food> findByShopIdAndNameContaining(Long shopId, String name);
    List<Food> findByPriceBetween(Double lower, Double higher);

}
