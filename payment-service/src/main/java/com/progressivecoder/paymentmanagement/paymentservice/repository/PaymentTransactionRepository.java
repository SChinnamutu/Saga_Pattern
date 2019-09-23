package com.progressivecoder.paymentmanagement.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progressivecoder.paymentmanagement.paymentservice.entity.PaymentTransaction;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

	public PaymentTransaction findBytxnUniqueId(String txnUniqueId);
}
