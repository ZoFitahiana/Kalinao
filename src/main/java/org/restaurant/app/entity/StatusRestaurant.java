package org.restaurant.app.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StatusRestaurant {
    private int id;
    private String location;
    private int crepesVendus;
    private int gaufresVendus;
    private int croissantsVendus;
    private double montantCrepes;
    private double montantGaufres;
    private double montantCroissants;
}
