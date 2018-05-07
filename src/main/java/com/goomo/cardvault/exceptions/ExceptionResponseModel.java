package com.goomo.cardvault.exceptions;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.goomo.cardvault.dto.StatusMessage;

@Component
public class ExceptionResponseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String status;
	private StatusMessage statusMessage;
	
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
