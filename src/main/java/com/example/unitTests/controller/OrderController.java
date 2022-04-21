package com.example.unitTests.controller;

import com.example.unitTests.model.Order;
import com.example.unitTests.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PutMapping("/{id}")
    public void placeOrder(@PathVariable Long id) {
        orderService.placeOrder(id);
    }

    @GetMapping("/history/{id}")
    public List<Order> orderHistory(@PathVariable Long id) {
        return orderService.orderHistory(id);
    }
}
