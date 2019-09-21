package com.progressivecoder.ordermanagement.orderservice.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;
import com.progressivecoder.ordermanagement.orderservice.services.commands.OrderCommandService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api/orders")
@Api(value = "Order Commands", description = "Order Commands Related Endpoints", tags = "Order Commands")
public class OrderCommandController {

    private OrderCommandService orderCommandService;

    public OrderCommandController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    public CompletableFuture<String> createOrder(@RequestBody OrderCreateDTO orderCreateDTO){
        return orderCommandService.createOrder(orderCreateDTO);
    }
    
    @DeleteMapping
    public CompletableFuture<String> deleteOrder(@RequestBody OrderDTO orderDTO){
        return orderCommandService.deleteOrder(orderDTO);
    }
}
