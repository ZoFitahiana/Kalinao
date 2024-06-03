package org.restaurant.app.service;

import org.restaurant.app.entity.Menu;
import org.restaurant.app.operation.MenuCrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    @Autowired
    private MenuCrudOperation menus ;

    public Menu saveMenu(Menu menu){
        return  menus.save(menu);
    }
}
