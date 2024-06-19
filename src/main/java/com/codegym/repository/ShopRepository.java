package com.codegym.repository;

import com.codegym.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findShopByUserId(Long id);
}
