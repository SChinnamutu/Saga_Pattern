package com.progressivecoder.shippingmanagement.shippingservice.services.commands;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;
import com.progressivecoder.shippingmanagement.shippingservice.entity.ShipTransaction;
import com.progressivecoder.shippingmanagement.shippingservice.repository.ShipTransactionRepository;

@Service
public class ShipServiceImpl implements ShipService {


	@Autowired
    private ShipTransactionRepository repository;
    
	@Override
    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO) {
		CompletableFuture<String> response = null;
		ShipTransaction transaction = new ShipTransaction();
		transaction.setStatus("APPROVED");
		transaction.setTxnCreatedDateAt(new Date());
		transaction.setTxnUniqueId(orderCreateDTO.getShippingId());
		transaction.setTxnOrderId(orderCreateDTO.getOrderId());
		transaction.setTxnPaymentId(orderCreateDTO.getPaymentId());
		repository.save(transaction);
		response  = new CompletableFuture<>();
		response.complete("SUCCESS");
		return response;
    }

	@Override
	public CompletableFuture<String> deleteOrder(OrderDTO orderDTO) {
		CompletableFuture<String> response = null;
		ShipTransaction transaction = repository.findBytxnUniqueId(orderDTO.getOrderId());
		if(transaction != null) {
			transaction.setStatus("CANCELLED");
			repository.save(transaction);
		}
		response  = new CompletableFuture<>();
		response.complete("SUCCESS");
		return response;
	}
	
	
}
