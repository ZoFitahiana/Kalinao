package org.restaurant.app.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientUsage {
    private int idIngredient;
    private String nomIngredient;
    private String menuPlusUtilise;
    private double quantiteDepensee;
    private String unite;
}
