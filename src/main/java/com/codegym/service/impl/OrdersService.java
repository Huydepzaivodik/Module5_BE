package com.codegym.service.impl;

import com.codegym.model.*;
import com.codegym.repository.*;
import com.codegym.service.IOrdersService;
import org.hibernate.query.Order;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.*;

@Service
public class OrdersService implements IOrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Orders> getPendingOrdersByShopId(Long id){
        List<Long> element =  ordersRepository.getOrdersByShopsContainingAndStatus(id,"PENDING");
        List<Orders> orders = new LinkedList<>();
        for (Long e : element){
            orders.add(ordersRepository.findById(e).get());
        }
        for (Orders elements : orders) {
            Set<OrderProduct> foods = new HashSet<>();
            for (OrderProduct orderProduct : elements.getFoods()) {
                if (orderProduct.getProduct().getShop().getId().equals(id))
                    foods.add(orderProduct);
            }
            elements.setFoods(foods);
        }
         return orders;
    }

    @Override
    public Orders getOrderByShop(Long id, Long shop) {
        Optional<Orders> optionalOrders = ordersRepository.findById(id);
        Orders orders = new Orders();
        if(optionalOrders.isPresent())
            orders = optionalOrders.get();
        List<Long> food_ids = orderProductRepository.findOrderProductsByOrder(id);
        List<Integer> quantitys = orderProductRepository.findQuantityByOrder(id);
            Set<OrderProduct> foods = new HashSet<>();
            for (int i = 0 ; i < food_ids.size();i++){
                OrderProduct orderProduct = new OrderProduct();
                Food food = foodRepository.findById(food_ids.get(i)).get();
                    OrderProductPK o = new OrderProductPK();
                    o.setFood(food);
                    o.setOrder(orders);
                    orderProduct.setOrderProductPK(o);
                    orderProduct.setQuantity(quantitys.get(i));
                    foods.add(orderProduct);
            }
            System.out.println(foods);
            orders.setFoods(foods);
            Set<Coupon> coupons = new HashSet<>();
            for(Coupon coupon: orders.getCoupons()){
                if(coupon.getShop().getId().equals(shop))
                    coupons.add(coupon);
            }
            orders.setCoupons(coupons);
            return orders;
    }

    @Override
    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    @Override
    public Orders findById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }
    @Override
    public void save(Orders orders) {
        Orders newOrder = ordersRepository.save(new Orders());
       newOrder.setUser(userService.findById(orders.getUser().getId()).get());
       Optional<Delivery> delivery = deliveryRepository.findById(orders.getDelivery().getId());
       newOrder.setDelivery(delivery.get());
//     SET SHOPS BY FOODS
        Set<OrderProduct> products = orders.getFoods();
       double sub = 0;
        Set<Coupon> coupons = new HashSet<>();
       for (Coupon coupon: orders.getCoupons()){
            coupons.add(couponRepository.findById(coupon.getId()).get());
       }
       Set<Shop> shops = new HashSet<>();
       newOrder.setCoupons(coupons);

       for(OrderProduct orderProduct: products){
           shops.add(orderProduct.getProduct().getShop());
           for(Coupon coupon: orders.getCoupons()){
               coupon = couponRepository.findById(coupon.getId()).get();
               if(coupon.getShop().getId().equals(orderProduct.getProduct().getShop().getId())){
                   if(coupon.getType().toUpperCase().equals("MINUS"))
                       sub += orderProduct.getTotalPrice() - coupon.getDiscount();
                   else if(coupon.getType().toUpperCase().equals("PERCENT"))
                       sub += orderProduct.getTotalPrice() - orderProduct.getTotalPrice() * ((coupon.getDiscount())/100);
               }
           }
       }
        newOrder.setShops(shops);
       double delivery_cost = newOrder.getDelivery().getCost()* newOrder.getShops().size();
       newOrder.setTotal(delivery_cost+ sub);
       newOrder.setDate(new Date(System.currentTimeMillis()));
       newOrder.setStatus(orders.getStatus());
       newOrder.setShippingAddress(orders.getShippingAddress());
        System.out.println(newOrder);
        for (OrderProduct orderProduct: products){
            orderProduct.getOrderProductPK().setOrder(newOrder);
            orderProductRepository.save(orderProduct);
            Food food = orderProduct.getProduct();
            food.setQuantity(foodRepository.findById(food.getId()).get().getQuantity() + orderProduct.getQuantity());
            foodRepository.save(food);
        }
        System.out.println(newOrder.getShops());
       ordersRepository.save(newOrder);
    }

    @Override
    public void remove(Long id) {
           ordersRepository.deleteById(id);
    }
}
