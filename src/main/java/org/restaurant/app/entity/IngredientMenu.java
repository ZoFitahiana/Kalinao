package org.restaurant.app.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class IngredientMenu {
    private int idIngredientMenu ;
    private int idMenu ;
    private int idIngredient ;
    private int quantiteNecessaire ;
    private  int idUnite ;
}

