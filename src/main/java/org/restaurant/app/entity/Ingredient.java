package org.restaurant.app.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Ingredient {
    private int idIngredient ;
    private String nom ;
    private double prix ;
    private int idUnite ;
    private double stock ;
}
