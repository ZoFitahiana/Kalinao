package org.restaurant.app.Controller;

import org.restaurant.app.entity.StatusRestaurant;
import org.restaurant.app.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @GetMapping("/status-menu-restaurant")
    public List<StatusRestaurant> getStatusMenuRestaurant(@RequestParam  LocalDateTime start, @RequestParam LocalDateTime end){
        return restaurantService.getStatusMenuRestaurant(start,end);
    }
}
