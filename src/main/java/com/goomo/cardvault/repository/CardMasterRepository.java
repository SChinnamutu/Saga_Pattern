package com.goomo.cardvault.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.goomo.cardvault.model.CardMaster;

/**
 * This class used to create CRUD operations for card master table.
 * @author Manjunath Jakkandi
 *
 */
public interface CardMasterRepository extends CrudRepository<CardMaster, Long> {
	
	@Query("SELECT c FROM CardMaster c where c.uniqueCardId = :uniqueCardId")
	public CardMaster findByUniqueCardId(@Param("uniqueCardId") String uniqueCardId);
	
	@Query("SELECT c FROM CardMaster c where c.cardToken = :cardToken")
	public CardMaster findByCardToken(@Param("cardToken") String cardToken);
	
	@Query("SELECT c FROM CardMaster c where c.userId = :userId")
	public List<CardMaster> findByUserId(@Param("userId") String userId);
	
	@Query("SELECT c FROM CardMaster c where c.uniqueCardId = :uniqueCardId and c.userId = :userId")
	public CardMaster findByUniqueCardIdAndUserId(@Param("uniqueCardId") String uniqueCardId, @Param("userId") String userId);
	
	@Query("SELECT c FROM CardMaster c where c.cardToken = :cardToken and c.userId = :userId")
	public CardMaster findByCardTokenAndUserId(@Param("cardToken") String cardToken, @Param("userId") String userId);
	
}
