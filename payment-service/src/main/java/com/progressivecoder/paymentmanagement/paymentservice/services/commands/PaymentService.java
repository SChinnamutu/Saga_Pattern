package com.progressivecoder.paymentmanagement.paymentservice.services.commands;

import java.util.concurrent.CompletableFuture;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;

public interface PaymentService {

    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO);

	public CompletableFuture<String> deleteOrder(OrderDTO orderDTO);

}
