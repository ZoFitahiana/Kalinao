package org.restaurant.app.Controller;

import org.restaurant.app.entity.IngredientMenu;
import org.restaurant.app.entity.IngredientUsage;
import org.restaurant.app.service.IngredientMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class IngredientMenuController {
    @Autowired
    private IngredientMenuService menu;

    @PostMapping("/ingredient-menu")
    public IngredientMenu Approvisionnement(@RequestBody IngredientMenu ingredientMenu) {
        return menu.register(ingredientMenu);
    }

    @GetMapping("/ingredient-use-more")
    public List<IngredientUsage> IngredientUseMore(@RequestParam LocalDateTime startDate, @RequestParam  LocalDateTime endDate){
        return menu.IngredientUseMore(startDate,endDate);
    }
}
