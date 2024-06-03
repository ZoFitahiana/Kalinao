package org.restaurant.app.service;

import org.restaurant.app.entity.Ingredient;
import org.restaurant.app.operation.IngredientCrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientCrudOperation ingredientCrudOperation;

    @Autowired
    public IngredientService(IngredientCrudOperation ingredientCrudOperation) {
        this.ingredientCrudOperation = ingredientCrudOperation;
    }

    public List<Ingredient> findAll() {
        return ingredientCrudOperation.findAll();
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientCrudOperation.save(ingredient);
    }

    public Ingredient update(Ingredient ingredient) {
        return ingredientCrudOperation.Update(ingredient);
    }

    public void delete(int id) {
        ingredientCrudOperation.delete(id);
    }
}
