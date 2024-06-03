package org.restaurant.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockMovement {
    private LocalDateTime dateMovement;
    private String ingredientName;
    private String type;
    private double quantity;
    private String unitName;

}
