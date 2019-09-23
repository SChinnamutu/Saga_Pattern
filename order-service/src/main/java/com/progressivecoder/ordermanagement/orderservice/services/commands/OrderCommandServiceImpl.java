package com.progressivecoder.ordermanagement.orderservice.services.commands;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progressivecoder.ecommerce.commands.CreateOrderCommand;
import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;
import com.progressivecoder.ordermanagement.orderservice.constants.OrderStatus;
import com.progressivecoder.ordermanagement.orderservice.entity.OrderTransaction;
import com.progressivecoder.ordermanagement.orderservice.repository.OrderTransactionRepository;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

	
	@Autowired
    private  CommandGateway commandGateway ;

	@Autowired
    private OrderTransactionRepository repository;
    
	@Override
    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO) {
		CompletableFuture<String> response = null;
		OrderTransaction transaction = null;
		if(orderCreateDTO.getRequestedBy() != null && orderCreateDTO.getRequestedBy().equalsIgnoreCase("CLIENT")) {
			response = commandGateway.send(new CreateOrderCommand(UUID.randomUUID().toString(), orderCreateDTO.getItemType(),
	                orderCreateDTO.getPrice(), orderCreateDTO.getCurrency(), String.valueOf(OrderStatus.CREATED)));
		}else if(orderCreateDTO.getRequestedBy() != null && 
				orderCreateDTO.getRequestedBy().equalsIgnoreCase("ADMIN")) {
			transaction = new OrderTransaction();
			transaction.setCurrency(orderCreateDTO.getCurrency());
			transaction.setStatus("APPROVED");
			transaction.setTxnAmount(orderCreateDTO.getPrice());
			transaction.setTxnCreatedDateAt(new Date());
			transaction.setTxnProductType(orderCreateDTO.getItemType());
			transaction.setTxnUniqueId(orderCreateDTO.getOrderId());
			repository.save(transaction);
			response  = new CompletableFuture<>();
			response.complete("SUCCESS");
		}
		return response;
    }

	@Override
	public CompletableFuture<String> deleteOrder(OrderDTO orderDTO) {
		CompletableFuture<String> response = null;
		OrderTransaction transaction = repository.findBytxnUniqueId(orderDTO.getOrderId());
		if(transaction != null) {
			transaction.setStatus("CANCELLED");
			repository.save(transaction);
		}
		response  = new CompletableFuture<>();
		response.complete("SUCCESS");
		return response;
	}
	
	
}
