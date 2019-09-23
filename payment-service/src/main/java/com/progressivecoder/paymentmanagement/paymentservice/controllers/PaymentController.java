package com.progressivecoder.paymentmanagement.paymentservice.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;
import com.progressivecoder.paymentmanagement.paymentservice.services.commands.PaymentService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api/payments")
@Api(value = "Payment Commands", description = "Payment Commands Related Endpoints", tags = "Payment Commands")
public class PaymentController {

    private PaymentService orderCommandService;

    public PaymentController(PaymentService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    public CompletableFuture<String> createPayment(@RequestBody OrderCreateDTO orderCreateDTO){
        return orderCommandService.createOrder(orderCreateDTO);
    }
    
    @DeleteMapping
    public CompletableFuture<String> deletePayment(@RequestBody OrderDTO orderDTO){
        return orderCommandService.deleteOrder(orderDTO);
    }
}
