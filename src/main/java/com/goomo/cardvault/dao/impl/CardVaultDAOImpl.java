package com.goomo.cardvault.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goomo.cardvault.dao.CardVaultDAO;
import com.goomo.cardvault.model.CardMaster;
import com.goomo.cardvault.repository.CardMasterRepository;

/**
 * This class used to implement DB transactions for card vault functions.
 * @author Manjunath Jakkandi
 *
 */

@Service
public class CardVaultDAOImpl implements CardVaultDAO {

	
	@Autowired
	private CardMasterRepository cardMasterRepository;
	
	@Override
	public List<CardMaster> fetchCardDetailsByUserId(String userId) throws Exception {
		List<CardMaster> cardMasterList = cardMasterRepository.findByUserId(userId);
		return cardMasterList;
	}

	@Override
	public CardMaster fetchCardDetailsByToken(String token) throws Exception {
		CardMaster cardMaster = cardMasterRepository.findByCardToken(token);
		return cardMaster;
	}

	@Override
	public CardMaster fetchCardDetailsByUniqueId(String cardUniqueId) throws Exception {
		CardMaster cardMaster = cardMasterRepository.findByUniqueCardId(cardUniqueId);
		return cardMaster;
	}

	@Override
	public CardMaster storeCard(CardMaster cardMaster) throws Exception {
		CardMaster savedCardMaster = cardMasterRepository.save(cardMaster);
		return savedCardMaster;
	}

	@Override
	public void deleteCard(CardMaster cardMaster) throws Exception {
		cardMasterRepository.delete(cardMaster);
	}

	@Override
	public CardMaster fetchCardDetailsByTokenAndUserId(String token, String userId) throws Exception {
		CardMaster cardMaster = cardMasterRepository.findByCardTokenAndUserId(token, userId);
		return cardMaster;
	}

	@Override
	public CardMaster fetchCardDetailsByUniqueIdAndUserId(String cardUniqueId, String userId) throws Exception {
		CardMaster cardMaster = cardMasterRepository.findByUniqueCardIdAndUserId(cardUniqueId, userId);
		return cardMaster;
	}
	

}
