package com.codegym.controller;

import com.codegym.model.Coupon;
import com.codegym.model.Orders;
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

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ordersService.remove(id);
        String message = "Delete success";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getFoodById(@PathVariable Long id) {
        Orders orders = ordersService.findById(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

}
