package com.goomo.cardvault.handlers;

import org.springframework.stereotype.Component;

import com.goomo.cardvault.dto.StatusMessage;

/**
 * This class used to throw custom exception for bad request and invalid response.
 * @author Manjunath Jakkandi
 *
 */
@Component
public class CustomException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String status;
	private StatusMessage statusMessage;
	
	public CustomException() {
		super();
	}
	
	public CustomException(String s) {
		super(s);
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public StatusMessage getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(StatusMessage statusMessage) {
		this.statusMessage = statusMessage;
	}
	
}
