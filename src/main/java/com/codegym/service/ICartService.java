package com.codegym.service;

import com.codegym.model.Cart;

public interface ICartService extends IGenerateService<Cart>{

      Cart findByUserId(Long id);

      void removeAllById(Long id);
}
