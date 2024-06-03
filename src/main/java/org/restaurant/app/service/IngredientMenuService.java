package org.restaurant.app.service;

import org.restaurant.app.entity.IngredientMenu;
import org.restaurant.app.entity.IngredientUsage;
import org.restaurant.app.operation.IngredientMenuCrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IngredientMenuService {
    @Autowired
    private IngredientMenuCrudOperation ingredientMenu;

    public IngredientMenu register(IngredientMenu menu) {
        return ingredientMenu.save(menu);
    }
    public List<IngredientUsage> IngredientUseMore(LocalDateTime startDate, LocalDateTime endDate){
        return ingredientMenu.getIngredientUsage(startDate,endDate);
    }
}
