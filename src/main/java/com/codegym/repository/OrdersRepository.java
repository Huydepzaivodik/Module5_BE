package com.codegym.repository;

import com.codegym.model.Orders;
import com.codegym.model.Role;
import com.codegym.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query(value = "select o.id from orders o join orders_shops os on os.orders_id = o.id join order_product op on op.order_id = o.id where os.shops_id = ?1",nativeQuery = true)
    Set<Long> getOrdersByShopsContainingAndStatus(Long id);

    @Query(value = "select orders_id from orders_shops where shops_id = ?1",nativeQuery = true)
    List<Long> getOrdersByShopId(Long id);

    @Query(value = "select o from Orders o where (o.user.name LIKE %:str%  or o.user.phoneNumber like %:str%) and (o.delivery.id in :ships) and o.date between :start and :time_end")
    List<Orders> filterOrders(@Param("str") String str, @Param("ships") Collection<Long> ships,@Param("start") Date start, @Param("time_end") Date end);

    List<Orders> getOrdersByUserId(Long id);

    @Query(value = "select sum(total) from orders where user_id = ?1 and month(orders_date) = month(current_date()) and year(orders_date) = year(current_date())",nativeQuery = true)
    Long getTotalPriceByUserId(Long id);

    @Query(value = "select count(o.id) from orders o join coupons_orders co on co.orders_id = o.id where o.user_id = ?1 and month(orders_date) = month(current_date()) and year(orders_date) = year(current_date())",nativeQuery = true)
    Long getCouponQuantityUsedByUserId(Long id);

    @Query(value = "select count(id) from orders where user_id = ? and month(orders_date) = month(current_date()) and year(orders_date) = year(current_date())",nativeQuery = true)
    Long getOrdersQuantityByUserId(Long id);
    
}

