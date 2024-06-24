package com.codegym.controller;

import com.codegym.model.JwtResponse;
import com.codegym.model.Shop;
import com.codegym.model.User;
import com.codegym.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("*")
@RequestMapping("/merchant")
public class ShopController {
    @Autowired
    IShopService shopService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Shop shop) {
           shopService.save(shop);
           return new ResponseEntity<>("SUCCESSED",HttpStatus.OK);
    }
    @GetMapping("/edit/{id}")
    public ResponseEntity<Shop> showEdit(@PathVariable Long id) {
        Shop shop = shopService.findByUserId(id);
        return new ResponseEntity<Shop>(shop,HttpStatus.OK);
    }
    @GetMapping("/edit")
    public ResponseEntity<?> showEdit(@RequestBody Shop shop) {
        shopService.save(shop);
        return new ResponseEntity<String>("OK",HttpStatus.OK);
    }

}
