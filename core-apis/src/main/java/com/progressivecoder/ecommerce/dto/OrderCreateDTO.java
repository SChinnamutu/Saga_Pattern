package com.progressivecoder.ecommerce.dto;

import java.math.BigDecimal;

public class OrderCreateDTO {

	private String shippingId;

	private String paymentId;

	private String requestedBy;

	private String orderId;

	private String currency;
	
	private String itemType;
	
	private BigDecimal price;
	
	
	public String getShippingId() {
		return shippingId;
	}

	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderCreateDTO [shippingId=" + shippingId + ", paymentId=" + paymentId + ", requestedBy=" + requestedBy
				+ ", orderId=" + orderId + ", currency=" + currency + ", itemType=" + itemType + ", price=" + price
				+ "]";
	}
	
	
	
    
}
