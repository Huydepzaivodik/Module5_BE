package com.codegym.service.impl;

import com.codegym.model.Coupon;
import com.codegym.repository.CouponRepository;
import com.codegym.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class CouponService implements ICouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Override
    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon findById(Long id) {
        return couponRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Coupon coupon) {
couponRepository.save(coupon);
    }

    @Override
    public void remove(Long id) {
couponRepository.deleteById(id);
    }

    @Override
    public List<Coupon> getCouponByFoodIdAndStatus(Long id, boolean status) {
        return couponRepository.getCouponsByShopIdIsAndStatus(id,status);
    }

    @Override
    public List<Coupon> getCouponByFoodId(Long id) {
        return couponRepository.getCouponsByShopIdIs(id);
    }
}
