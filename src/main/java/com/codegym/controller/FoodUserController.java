package com.codegym.controller;

import com.codegym.model.Food;
import com.codegym.model.Shop;
import com.codegym.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Food food = foodService.findById(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<?> getFoodByShopId(@PathVariable Long id) {
        List<Food> foodList = foodService.findByShopId(id);
        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }

    @GetMapping("/searchPrice")
    public ResponseEntity<List<Food>> searchPrice(@RequestParam("priceMin") Double priceMin, @RequestParam("priceMax") Double priceMax) {
        List<Food> foodList = foodService.findByPriceBetween(priceMin, priceMax);
        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }

    @GetMapping("/searchPriceAndShopId")
    public ResponseEntity<List<Food>> searchPriceAndShopId(@RequestParam("priceMin") Double priceMin, @RequestParam("priceMax") Double priceMax, @RequestParam("id_shop") Long id_shop) {
        List<Food> foodList = foodService.findByPriceBetweenAndShopId(priceMin, priceMax, id_shop);
        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }

    @GetMapping("/quantities/{id}")
    public ResponseEntity<?> getFoodByQuantitiesId(@PathVariable Long id) {
        Integer quantity = foodService.findTotalByFoodId(id);
        return new ResponseEntity<>(quantity, HttpStatus.OK);
    }


}
