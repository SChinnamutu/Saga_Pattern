package com.goomo.cardvault.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.goomo.cardvault.model.BinMaster;
import com.goomo.cardvault.model.CardMaster;

public interface BinMasterReposiroty extends CrudRepository<CardMaster, Long> {

	@Query("SELECT c FROM BinMaster c where c.binVal = :binVal")
	public BinMaster findByBinVal(@Param("binVal") String binVal);
	
	
}
