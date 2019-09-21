package com.progressivecoder.ecommerce.events;

public class InvoiceCreatedErrorEvent  {

	public final String paymentId;

	public final String orderId;

	public InvoiceCreatedErrorEvent(String paymentId, String orderId) {
		this.paymentId = paymentId;
		this.orderId = orderId;
	}
}
