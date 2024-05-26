package org.restaurant.app.utils;

import org.restaurant.app.entity.Ingredient;
import org.restaurant.app.entity.IngredientMenu;
import org.restaurant.app.operation.IngredientCrudOperation;
import org.springframework.stereotype.Component;


@Component
public class Stock {


    private static  IngredientCrudOperation ingredientCrudOperation;

    public Stock(IngredientCrudOperation ingredientCrudOperation) {
        this.ingredientCrudOperation = ingredientCrudOperation;
    }

    public static double stockIngredient(IngredientMenu menu){
        double stock = 0.0;
        if (menu.getType().equals("entrer")){
            stock += menu.getQuantiteNecessaire();
        }
        if (menu.getType().equals("sortie")){
            stock -= menu.getQuantiteNecessaire();
        }
        return stock;
    }
    public  static Ingredient updateStock(IngredientMenu menu){
     double stock = stockIngredient(menu);
     Ingredient ingredient = ingredientCrudOperation.findById(menu.getIdIngredient());
     Ingredient ingredientUpdateStock = new Ingredient(ingredient.getIdIngredient(), ingredient.getNom(),ingredient.getPrix(),ingredient.getIdUnite(),stock);
     ingredientCrudOperation.Update(ingredientUpdateStock);
     return  ingredientUpdateStock;
    }
}
