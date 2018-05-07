package com.goomo.cardvault.dto;

/**
 * BaseResponse class used for basic status responses
 * @author radhakrishnab
 *
 */

public class BaseResponse {
	private String status;
	private StatusMessage statusMessage;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the statusMessage
	 */
	public StatusMessage getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage
	 *            the statusMessage to set
	 */
	public void setStatusMessage(StatusMessage statusMessage) {
		this.statusMessage = statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[status=" + status + ", statusMessage=" + statusMessage.toString() + "]";
	}
}