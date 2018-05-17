package com.goomo.cardvault.dao;

import java.util.List;

import com.goomo.cardvault.model.CardMaster;

/**
 * This class used to define DB transactions for card vault functions.
 * @author Manjunath Jakkandi
 *
 */
public interface CardVaultDAO {

	public List<CardMaster> fetchCardDetailsByUserId(String userId) throws Exception;
	
	public CardMaster fetchCardDetailsByToken(String token) throws Exception;
	
	public CardMaster fetchCardDetailsByUniqueId(String cardUniqueId) throws Exception;
	
	public CardMaster storeCard(CardMaster cardMaster) throws Exception;
	
	public void deleteCard(CardMaster cardMaster) throws Exception;
	
	public CardMaster fetchCardDetailsByTokenAndUserId(String token, String userId) throws Exception;
	
	public CardMaster fetchCardDetailsByUniqueIdAndUserId(String cardUniqueId, String userId) throws Exception; 
	
}
