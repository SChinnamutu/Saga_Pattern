package com.progressivecoder.shippingmanagement.shippingservice.services.commands;

import java.util.Date;

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
    public String createOrder(OrderCreateDTO orderCreateDTO) {
		ShipTransaction transaction = new ShipTransaction();
		transaction.setStatus("APPROVED");
		transaction.setTxnCreatedDateAt(new Date());
		transaction.setTxnUniqueId(orderCreateDTO.getShippingId());
		transaction.setTxnOrderId(orderCreateDTO.getOrderId());
		transaction.setTxnPaymentId(orderCreateDTO.getPaymentId());
		repository.save(transaction);
		return "SUCCESS";
    }

	@Override
	public String deleteOrder(OrderDTO orderDTO) {
		ShipTransaction transaction = repository.findBytxnUniqueId(orderDTO.getOrderId());
		if(transaction != null) {
			transaction.setStatus("CANCELLED");
			repository.save(transaction);
		}
		return "SUCCESS";
	}
	
	
}
