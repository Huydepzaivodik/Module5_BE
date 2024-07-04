package com.codegym.repository;

import com.codegym.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
     Cart findCartByUserId(Long id);
}
