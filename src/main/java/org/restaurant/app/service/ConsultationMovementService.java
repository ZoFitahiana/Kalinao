package org.restaurant.app.service;

import org.restaurant.app.operation.IngredientCrudOperation;
import org.restaurant.app.operation.IngredientMenuCrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultationMovementService {
    @Autowired
    private IngredientMenuCrudOperation ingredientMenuCrudOperation;

    public  void ConsultationMovementOfStock(LocalDateTime start , LocalDateTime end){
        ingredientMenuCrudOperation.consultationMouvementStock(start,end);
    }
}
