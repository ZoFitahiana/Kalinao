package org.restaurant.app.Controller;

import org.restaurant.app.service.ConsultationMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ConsultationStockController {
    @Autowired
    private ConsultationMovementService consultationMovementService;
    @GetMapping("/consultation-stock")
    public void ConsultationMovementStock(@RequestParam LocalDateTime startDate,@RequestParam LocalDateTime endDate) {
         consultationMovementService.ConsultationMovementOfStock(startDate, endDate);
    }


}
