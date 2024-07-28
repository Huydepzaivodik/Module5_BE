package com.codegym.repository;

import com.codegym.model.Coupon;
import com.codegym.model.Role;
import com.codegym.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

     List<Coupon> getCouponsByShopIdIsAndStatus(Long id,boolean status);
     List<Coupon> getCouponsByShopIdIs(Long id);
}
