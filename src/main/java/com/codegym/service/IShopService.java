package com.codegym.service;

import com.codegym.model.Shop;

public interface IShopService extends IGenerateService<Shop>{
    Shop findByUserId(Long id);
}
