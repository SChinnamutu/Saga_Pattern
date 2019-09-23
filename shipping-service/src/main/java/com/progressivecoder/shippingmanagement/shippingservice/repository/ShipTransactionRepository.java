package com.progressivecoder.shippingmanagement.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progressivecoder.shippingmanagement.shippingservice.entity.ShipTransaction;

@Repository
public interface ShipTransactionRepository extends JpaRepository<ShipTransaction, Long> {

	public ShipTransaction findBytxnUniqueId(String txnUniqueId);
}
