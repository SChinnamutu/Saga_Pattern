package com.goomo.cardvault.dto;

/**
 * BaseResponse class used for basic status responses
 * @author Manjunath Jakkandi
 *
 */

public class BaseResponse {
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

	@Override
	public String toString() {
		return "[status=" + status + ", statusMessage=" + statusMessage.toString() + "]";
	}
}