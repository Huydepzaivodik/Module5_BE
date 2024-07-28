package com.codegym.repository;

import com.codegym.model.Orders;
import com.codegym.model.Role;
import com.codegym.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {


    @Query(value = "select o.id from orders o join orders_shops os on os.orders_id = o.id join order_product op on op.order_id = o.id where os.shops_id = ?1 and o.status = ?2",nativeQuery = true)
    List<Long> getOrdersByShopsContainingAndStatus(Long id,String status);

}

