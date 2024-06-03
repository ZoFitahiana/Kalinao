package org.restaurant.app.service;

import org.restaurant.app.entity.StockMovement;
import org.restaurant.app.operation.IngredientMenuCrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultationMovementService {
    @Autowired
    private IngredientMenuCrudOperation ingredientMenuCrudOperation;

    public List<StockMovement> ConsultationMovementOfStock(LocalDateTime start , LocalDateTime end){
       return ingredientMenuCrudOperation.getStockMovements(start,end);
    }
}
