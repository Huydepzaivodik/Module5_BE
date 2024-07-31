package com.codegym.service.impl;

import com.codegym.model.*;
import com.codegym.repository.*;
import com.codegym.service.IOrdersService;
import org.hibernate.query.Order;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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
    public Orders getOrder(Long id) {
        Orders orders = ordersRepository.findById(id).get();
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
        orders.setFoods(foods);
        return orders;
    }

    @Override
    public List<Orders> getOrdersByUserId(Long id) {
        List<Orders> orders = ordersRepository.getOrdersByUserId(id);
        List<Orders> back = new LinkedList<>();
        for (int i = 0 ; i < orders.size();i++){
            back.add(getOrder(orders.get(i).getId()));
        }
        return back;
    }

    @Override
    public List<Orders> getPendingOrdersByShopId(Long id){
        Set<Long> element =  ordersRepository.getOrdersByShopsContainingAndStatus(id);
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
    public void updateStatus(Long id) {
                String[] status = {"PENDING","DOING","SHIPPING","DONE"};
                Optional<Orders> optinal  = ordersRepository.findById(id);
                    Orders orders = optinal.get();

                    int index = -1;
                    for (int i = 0; i < status.length;i++){
                        if(orders.getStatus().equals(status[i]))
                             index = i;
                    }
                    if(index < status.length-1 && index != -1) orders.setStatus(status[index+1]);
                    ordersRepository.save(orders);
    }

    @Override
    public List<Orders> searchOrders(Long id, String type, String target) {
        List<Long> order_ids = ordersRepository.getOrdersByShopId(id);
        List<Orders> orders = new LinkedList<>();
        if(type.toUpperCase().equals("STATUS")){
            for (int i = 0; i < order_ids.size();i++){
                Orders orders1 = ordersRepository.findById(order_ids.get(i)).get();
                if(Objects.equals(orders1.getStatus(), target))
                      orders.add(ordersRepository.findById(order_ids.get(i)).get());
            }
            return orders;
        }
        else if(type.toUpperCase().equals("ID")){
            orders.add(ordersRepository.findById(Long.parseLong(target)).get());
            return orders;
        }else if(type.toUpperCase().equals("COUPON")){
            for (int i = 0; i < order_ids.size();i++){
                Orders orders1 = ordersRepository.findById(order_ids.get(i)).get();
                if(orders1.getCoupons().stream().toList().get(0).getId().equals(Long.parseLong(target)))
                    orders.add(orders1);
            }
            return orders;
        }
        return new LinkedList<>();
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
            orders.setFoods(foods);
            Set<Coupon> coupons = new HashSet<>();
            for(Coupon coupon: orders.getCoupons()){
                if(coupon.getShop().getId().equals(shop)){
                    coupons.add(coupon);
                    coupon.setQuantity(coupon.getQuantity() - 1);
                    couponRepository.save(coupon);
                }

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
        for (OrderProduct orderProduct: products){
            orderProduct.getOrderProductPK().setOrder(newOrder);
            orderProductRepository.save(orderProduct);
            Food food = orderProduct.getProduct();
            food.setQuantity(foodRepository.findById(food.getId()).get().getQuantity() + orderProduct.getQuantity());
            foodRepository.save(food);
        }

       ordersRepository.save(newOrder);
    }

    @Override
    public void remove(Long id) {
           ordersRepository.deleteById(id);
    }
}
