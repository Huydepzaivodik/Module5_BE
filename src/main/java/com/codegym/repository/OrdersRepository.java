package com.codegym.repository;

import com.codegym.model.Orders;
import com.codegym.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
