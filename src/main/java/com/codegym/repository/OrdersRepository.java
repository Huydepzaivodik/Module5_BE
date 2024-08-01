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


    @Query(value = "select os.orders_id from orders_shops os join orders o on o.id = os.orders_id where os.shops_id = ?1 and o.status = ?2",nativeQuery = true)
    List<Long> getOrdersByStatus(Long id,String status);

    @Query(value = "select co.orders_id from jwt.coupons_orders co join jwt.orders_shops os on os.orders_id = co.orders_id where co.coupons_id = ?2 and os.shops_id = ?1",nativeQuery = true)
    List<Long> getOrdersByCoupon(Long id,Long target);

    @Query(value = "select o from Orders o where (o.user.name LIKE %:str%  or o.user.phoneNumber like %:str%) and (o.delivery.id in :ships) and o.date between :start and :time_end")
    List<Orders> filterOrders(@Param("str") String str, @Param("ships") Collection<Long> ships,@Param("start") Date start, @Param("time_end") Date end);

    List<Orders> getOrdersByUserId(Long id);

}

