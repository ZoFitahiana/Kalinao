package org.restaurant.app.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Menu {
    private int idMenu ;
    private String name ;
    private double prixVente ;
}
