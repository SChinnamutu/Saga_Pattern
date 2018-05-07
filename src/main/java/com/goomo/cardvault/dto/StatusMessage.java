package com.goomo.cardvault.dto;

/**
 * This class used to set the api status code and description. Same parameters used across all the API's for
 * consistency.
 * @author Manjunath Jakkandi
 *
 */
public class StatusMessage {

	private String code;
	private String description;
	
	public StatusMessage() {
		super();
	}
	
	public StatusMessage(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[code=" + code + ", description=" + description + "]";
	}
}