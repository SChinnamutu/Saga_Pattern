package com.goomo.cardvault.model;

import java.io.Serializable;

/**
 * This class used to set data source parameters for card vault service.
 * @author Manjunath Jakkandi
 *
 */
public class DataSourceModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String jwtSecretKey;
	private String gatewayServiceBaseURL;
	private String aesSecretKey;
	
	public String getJwtSecretKey() {
		return jwtSecretKey;
	}
	public void setJwtSecretKey(String jwtSecretKey) {
		this.jwtSecretKey = jwtSecretKey;
	}
	public String getGatewayServiceBaseURL() {
		return gatewayServiceBaseURL;
	}
	public void setGatewayServiceBaseURL(String gatewayServiceBaseURL) {
		this.gatewayServiceBaseURL = gatewayServiceBaseURL;
	}
	public String getAesSecretKey() {
		return aesSecretKey;
	}
	public void setAesSecretKey(String aesSecretKey) {
		this.aesSecretKey = aesSecretKey;
	}
	
	

	
}
