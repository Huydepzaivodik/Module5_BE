    package com.codegym.controller;

    import com.codegym.model.Food;
    import com.codegym.service.IFoodService;
    import com.codegym.service.impl.FoodService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;


    @Controller
    @CrossOrigin("*")
    @RequestMapping("/foods")
    public class FoodController {
    @Autowired
    private IFoodService foodService;

        @GetMapping("")
        public ResponseEntity<?> getAll(){
            List<Food> list = foodService.findAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }

        @PostMapping("")
        public ResponseEntity<String> add(@RequestBody Food food) {
            foodService.save(food);
            String message = "Add success";
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }

        @PutMapping("{id}")
        public ResponseEntity<String> edit(@PathVariable Long id, @RequestBody Food food) {
            food.setId(id);
            foodService.save(food);
            String message = "Edit success";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    

    @PostMapping
    public ResponseEntity<String> addFood(@RequestBody Food food) {
        foodService.save(food);
        String message = "Add success";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        foodService.remove(id);
        String message = "Delete success";
    return new ResponseEntity<>(message,HttpStatus.OK);
    }
    
    }