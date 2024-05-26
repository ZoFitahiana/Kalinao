package org.restaurant.app.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class IngredientMenu {
    private int idIngredientMenu ;
    private int idMenu ;
    private int idIngredient ;
    private double quantiteNecessaire ;
    private String type ;
    private  int idUnite ;
    private LocalDateTime dateMovement ;
}

