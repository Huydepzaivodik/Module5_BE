package com.codegym.repository;

import com.codegym.model.Coupon;
import com.codegym.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
