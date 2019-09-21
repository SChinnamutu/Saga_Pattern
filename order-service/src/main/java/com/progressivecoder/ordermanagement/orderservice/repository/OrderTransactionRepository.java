package com.progressivecoder.ordermanagement.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progressivecoder.ordermanagement.orderservice.entity.OrderTransaction;

@Repository
public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Long> {

	public OrderTransaction findBytxnUniqueId(String txnUniqueId);
}
