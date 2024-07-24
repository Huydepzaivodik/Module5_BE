package com.codegym.repository;

import com.codegym.model.Delivery;
import com.codegym.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
