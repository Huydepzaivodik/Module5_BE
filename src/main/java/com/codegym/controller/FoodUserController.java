package com.codegym.controller;

import com.codegym.model.Food;
import com.codegym.model.Shop;
import com.codegym.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("/user/foods")
public class FoodUserController {
    @Autowired
    private IFoodService foodService;

    @GetMapping("")
    public ResponseEntity<List<Food>> getAllFoods() {
       List<Food> foodList = foodService.findAll();
       return new ResponseEntity<>(foodList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> search(@RequestParam("foodName") String name) {
        List<Food> list = foodService.findByNameContaining(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
