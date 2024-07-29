package com.codegym.repository;

import com.codegym.model.Cart;
import com.codegym.model.Food;
import com.codegym.model.Orders;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
     Optional<Cart> findCartByUserId(Long id);

}
