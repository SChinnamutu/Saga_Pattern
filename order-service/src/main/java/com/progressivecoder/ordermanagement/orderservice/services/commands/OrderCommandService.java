package com.progressivecoder.ordermanagement.orderservice.services.commands;

import java.util.concurrent.CompletableFuture;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;

public interface OrderCommandService {

    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO);

	public CompletableFuture<String> deleteOrder(OrderDTO orderDTO);

}
