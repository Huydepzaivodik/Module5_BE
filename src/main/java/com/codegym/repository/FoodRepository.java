package com.codegym.repository;

import com.codegym.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {
    List<Food> findByShopId(Long shopId);

    List<Food> findByNameContaining(String name);
    List<Food> findByShopIdAndNameContaining(Long shopId, String name);

    @Query(value = "select f from Food f where f.shop.id = :id")
    List<Food> getSameFoodsByShopId(@Param("id") Long id);

    @Query(value = "select fiq.food_id from (select op.food_id, sum(quantity) as quantity from order_product op join orders o on o.id = op.order_id where o.orders_date = current_date()  group by op.food_id,o.orders_date) as fiq order by fiq.quantity desc",nativeQuery = true)
    List<Long> getBestsellerFoodToday();

}
