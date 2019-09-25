package com.progressivecoder.ordermanagement.orderservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;
import com.progressivecoder.ordermanagement.orderservice.services.commands.OrderCommandService;

import io.swagger.annotations.Api;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/orders")
@Api(value = "Order Commands", description = "Order Commands Related Endpoints", tags = "Order Commands")
public class OrderCommandController {

    private OrderCommandService orderCommandService;

    public OrderCommandController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderCreateDTO orderCreateDTO){
        return ResponseEntity.ok(orderCommandService.createOrder(orderCreateDTO));
    }
    
    @PostMapping
    public ResponseEntity<String> updateOrder(@RequestBody OrderCreateDTO orderCreateDTO){
        return ResponseEntity.ok(orderCommandService.updateOrder(orderCreateDTO));
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteOrder(@RequestBody OrderDTO orderDTO){
    	 return ResponseEntity.ok(orderCommandService.deleteOrder(orderDTO));
    }
}
