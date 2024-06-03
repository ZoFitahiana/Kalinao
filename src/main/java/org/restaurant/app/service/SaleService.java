package org.restaurant.app.service;

import org.restaurant.app.entity.Ingredient;
import org.restaurant.app.entity.IngredientMenu;
import org.restaurant.app.entity.Menu;
import org.restaurant.app.operation.IngredientCrudOperation;
import org.restaurant.app.operation.IngredientMenuCrudOperation;
import org.restaurant.app.operation.MenuCrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private IngredientMenuCrudOperation ingredientMenuCrudOperation;

    @Autowired
    private IngredientCrudOperation ingredientCrudOperation;

    @Autowired
    private MenuCrudOperation menuCrudOperation;

    public double saleOfIngredientMenu(List<IngredientMenu> order) {
        for (IngredientMenu ingredientMenu : order) {
            Ingredient ingredient = ingredientCrudOperation.findById(ingredientMenu.getIdIngredient());
            if (ingredient.getStock() < ingredientMenu.getQuantiteNecessaire()) {
                throw new RuntimeException("Insufficient stock for ingredient ID: " + ingredientMenu.getIdIngredient());
            }
        }

        double totalPrice = 0.00;
        for (IngredientMenu ingredientMenu : order) {
            Menu menu = menuCrudOperation.findById(ingredientMenu.getIdMenu());
            ingredientMenuCrudOperation.save(ingredientMenu);
            totalPrice += menu.getPrixVente();
        }

        return totalPrice;
    }
}
