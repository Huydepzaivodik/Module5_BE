package com.codegym.repository;

import com.codegym.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByShopId(Long shopId);

    List<Food> findByNameContaining(String name);

    List<Food> findByShopIdAndNameContaining(Long shopId, String name);

    List<Food> findByPriceBetween(Double lower, Double higher);

    @Query("SELECT f FROM Food f WHERE f.price BETWEEN :lower AND :higher AND f.shop.id = :shopId")
    List<Food> findByPriceBetweenAndShopId(Double lower, Double higher, Long shopId);


    @Query(value = "select f from Food f where f.shop.id IN :shopIds")
    //sua thang Collection
    List<Food> findByShopIds(@Param("shopIds") List<Long> shopIds);

    @Query(value =
            "select sum(quantity) from orders o " +
            "inner join order_product op on op.order_id = o.id " +
            "where op.food_id= :food_id " +
            "group by op.food_id",nativeQuery = true
    )
    Integer findTotalByFoodId(@Param("food_id") Long food_id);
}
