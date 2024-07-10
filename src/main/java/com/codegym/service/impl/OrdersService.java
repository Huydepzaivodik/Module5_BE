package com.codegym.service.impl;

import com.codegym.model.Orders;
import com.codegym.repository.OrdersRepository;
import com.codegym.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService implements IOrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

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
       ordersRepository.save(orders);
    }

    @Override
    public void remove(Long id) {
ordersRepository.deleteById(id);
    }
}
