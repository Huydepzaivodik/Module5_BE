package com.codegym.controller;

import com.codegym.model.Coupon;
import com.codegym.model.Food;
import com.codegym.model.Orders;
import com.codegym.model.Shop;
import com.codegym.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("/orders")

public class OrdersController {
    @Autowired
    private IOrdersService ordersService;
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<Orders> list = ordersService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> add(@RequestBody Orders orders) {
        ordersService.save(orders);
        String message = "Add success";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> edit(@PathVariable Long id, @RequestBody Orders orders) {
        orders.setId(id);
        ordersService.save(orders);
        String message = "Edit success";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id){
           ordersService.updateStatus(id);
           System.out.println(ordersService.findById(id));
          return new ResponseEntity<>("",HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelStatus(@PathVariable Long id){
        Orders orders = ordersService.findById(id);
        orders.setStatus("CANCEL");
        ordersService.save(orders);
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ordersService.remove(id);
        String message = "Delete success";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrdersById(@PathVariable Long id) {
        Orders orders = ordersService.getOrder(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable Long id){
           List<Orders> orders = ordersService.getOrdersByUserId(id);
           return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<List<Orders>> getOrdersByShop(@PathVariable Long id){
           List<Orders> orders = ordersService.getPendingOrdersByShopId(id);
           return new ResponseEntity<List<Orders>>(orders,HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity<Orders> getOrderByShop(@RequestParam(name = "order_id") Long id, @RequestParam(name = "shop_id") Long shop){
        Orders orders = ordersService.getOrderByShop(id,shop);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<Orders>> searchOrder(@RequestParam Long shop_id,@RequestParam(name = "type") String type,@RequestParam(name = "target")String target){
           List<Orders> orders  = ordersService.searchOrders(shop_id,type,target);
           return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
