package com.progressivecoder.ecommerce.dto;

public class OrderDTO {
    
    
    private String requestedBy;
    
    private String orderId;

    public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderDTO [requestedBy=" + requestedBy + ", orderId=" + orderId + "]";
	}
	
	
    
}
