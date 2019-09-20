package com.progressivecoder.ordermanagement.orderservice.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.progressivecoder.ordermanagement.orderservice.entity.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	public Transaction findBytxnUniqueId(String txnUniqueId);
}
