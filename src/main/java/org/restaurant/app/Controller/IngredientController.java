package org.restaurant.app.Controller;

import org.restaurant.app.entity.Ingredient;
import org.restaurant.app.entity.IngredientUsage;
import org.restaurant.app.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/all-ingredient")
    public List<Ingredient> getAllIngredients() {
        return ingredientService.findAll();
    }

    @PostMapping("/register-ingredient")
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.save(ingredient);
    }

    @PutMapping("/update-ingredient")
    public Ingredient updateIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.update(ingredient);
    }

    @DeleteMapping("/ingredient/{id}")
    public void deleteIngredient(@PathVariable int id) {
        ingredientService.delete(id);
    }
}
