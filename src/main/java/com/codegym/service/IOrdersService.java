package com.codegym.service;

import com.codegym.model.Orders;
import com.codegym.model.Shop;
import org.aspectj.weaver.ast.Or;
import org.hibernate.query.Order;

import java.util.Date;
import java.util.List;

public interface IOrdersService extends IGenerateService<Orders>{
    List<Orders> getPendingOrdersByShopId(Long id);

    Orders getOrderByShop(Long id,Long shop);

    List<Orders> searchOrders(Long id,String type,String target);

    void updateStatus(Long id);

    List<Orders> getOrdersByUserId(Long id);

    Orders getOrder(Long id);

    List<Orders> filterOrders(String str, String ship, List<Date> dates);
}
