package org.restaurant.app.Controller;

import org.restaurant.app.entity.IngredientMenu;
import org.restaurant.app.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class saleController {
    @Autowired
    private SaleService sale ;

    @PostMapping("/sale-menu")
    public double saleMenu(@RequestBody List<IngredientMenu> order){
        return  sale.saleOfIngredientMenu(order);
    }
}
