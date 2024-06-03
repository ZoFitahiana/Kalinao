package org.restaurant.app.service;

import org.restaurant.app.entity.StatusRestaurant;
import org.restaurant.app.operation.RestaurantOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantOperation restaurantOperation;

    public List<StatusRestaurant> getStatusMenuRestaurant(LocalDateTime start,LocalDateTime end){
        return restaurantOperation.getStatusRestaurant(start,end);
    }
}
