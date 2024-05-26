package org.restaurant.app.service;

import org.restaurant.app.entity.IngredientMenu;
import org.restaurant.app.operation.IngredientCrudOperation;
import org.restaurant.app.operation.IngredientMenuCrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientMenuService {
    @Autowired
    private static IngredientMenuCrudOperation ingredientMenu ;

    public  IngredientMenu register(IngredientMenu menu){
        return  ingredientMenu.save(menu);
    }
}
