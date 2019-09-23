package com.progressivecoder.paymentmanagement.paymentservice.services.commands;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;
import com.progressivecoder.paymentmanagement.paymentservice.entity.PaymentTransaction;
import com.progressivecoder.paymentmanagement.paymentservice.repository.PaymentTransactionRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
    private PaymentTransactionRepository repository;
    
	@Override
    public String createOrder(OrderCreateDTO orderCreateDTO) {
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setTxnCreatedDateAt(new Date());
		transaction.setTxnOrderId(orderCreateDTO.getOrderId());
		transaction.setTxnUniqueId(orderCreateDTO.getPaymentId());
		transaction.setStatus("APPROVED");
		repository.save(transaction);
		return "SUCCESS";
    }

	@Override
	public String deleteOrder(OrderDTO orderDTO) {
		PaymentTransaction transaction = repository.findBytxnUniqueId(orderDTO.getOrderId());
		if(transaction != null) {
			transaction.setStatus("CANCELLED");
			repository.save(transaction);
		}
		return "SUCCESS";
	}
	
	
}
