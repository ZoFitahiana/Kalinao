package org.restaurant.app.Controller;

import org.restaurant.app.entity.IngredientMenu;
import org.restaurant.app.service.IngredientMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngredientMenuController {
    @Autowired
    private IngredientMenuService menu;

    @PostMapping("/ingredient-menu")
    public IngredientMenu Approvisionnement(@RequestBody IngredientMenu ingredientMenu) {
        return menu.register(ingredientMenu);
    }
}
