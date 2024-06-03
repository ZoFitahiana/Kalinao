package org.restaurant.app.Controller;

import org.restaurant.app.entity.Menu;
import org.restaurant.app.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;
    @PostMapping("/register-menu")
   public Menu register(@RequestBody Menu menu){
       return  menuService.saveMenu(menu);
   }
}
