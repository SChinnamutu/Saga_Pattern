package com.progressivecoder.shippingmanagement.shippingservice.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;
import com.progressivecoder.shippingmanagement.shippingservice.services.commands.ShipService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api/ship")
@Api(value = "Ship Commands", description = "Ship Commands Related Endpoints", tags = "Ship Commands")
public class ShipController {

    private ShipService orderCommandService;

    public ShipController(ShipService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    public CompletableFuture<String> createShippingOrder(@RequestBody OrderCreateDTO orderCreateDTO){
        return orderCommandService.createOrder(orderCreateDTO);
    }
    
    @DeleteMapping
    public CompletableFuture<String> deleteShippingOrder(@RequestBody OrderDTO orderDTO){
        return orderCommandService.deleteOrder(orderDTO);
    }
}
