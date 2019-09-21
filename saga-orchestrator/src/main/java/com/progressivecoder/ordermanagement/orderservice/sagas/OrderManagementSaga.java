package com.progressivecoder.ordermanagement.orderservice.sagas;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import com.progressivecoder.ecommerce.commands.CreateFailureInvoiceCommand;
import com.progressivecoder.ecommerce.commands.CreateFailureOrderCommand;
import com.progressivecoder.ecommerce.commands.CreateFailureShippingCommand;
import com.progressivecoder.ecommerce.commands.CreateInvoiceCommand;
import com.progressivecoder.ecommerce.commands.CreateShippingCommand;
import com.progressivecoder.ecommerce.commands.UpdateOrderStatusCommand;
import com.progressivecoder.ecommerce.events.InvoiceCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderShippedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;
import com.progressivecoder.ordermanagement.orderservice.constants.OrderStatus;

@Saga
public class OrderManagementSaga {

	private Logger logger = Logger.getLogger(OrderManagementSaga.class.getName());
	
    @Inject
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) throws InterruptedException, ExecutionException{
    	logger.info("OrderManagementSaga :: OrderCreatedEvent invoked");
    	String paymentId = UUID.randomUUID().toString();
        logger.info("order id" + orderCreatedEvent.orderId);
        SagaLifecycle.associateWith("paymentId", paymentId);
        CompletableFuture<String> invoiceResponse =   commandGateway.send(new CreateInvoiceCommand(paymentId, orderCreatedEvent.orderId));
        if(invoiceResponse != null && invoiceResponse.isCompletedExceptionally()) {
        	commandGateway.send(new CreateFailureOrderCommand(orderCreatedEvent.orderId));
        	SagaLifecycle.end();
        }
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(InvoiceCreatedEvent invoiceCreatedEvent){
    	logger.info("OrderManagementSaga :: InvoiceCreatedEvent invoked");
    	String shippingId = UUID.randomUUID().toString();
        logger.info("Saga continued");
        SagaLifecycle.associateWith("shipping", shippingId);
        CompletableFuture<String> shippingResponse =  commandGateway.send(new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId, invoiceCreatedEvent.paymentId));
        if(shippingResponse != null && shippingResponse.isCompletedExceptionally()) {
        	commandGateway.send(new CreateFailureInvoiceCommand(invoiceCreatedEvent.paymentId,invoiceCreatedEvent.orderId));
        	commandGateway.send(new CreateFailureOrderCommand(invoiceCreatedEvent.orderId));
        	SagaLifecycle.end();
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    //@SagaEventHandler(associationProperty = "shipping")
    public void handle(OrderShippedEvent orderShippedEvent){
    	logger.info("OrderManagementSaga :: OrderShippedEvent invoked");
    	CompletableFuture<String> updateOrderResponse = commandGateway.send(new UpdateOrderStatusCommand(orderShippedEvent.orderId, String.valueOf(OrderStatus.SHIPPED)));
    	if(updateOrderResponse != null && updateOrderResponse.isCompletedExceptionally()) {
    		commandGateway.send(new CreateFailureShippingCommand(orderShippedEvent.orderId,orderShippedEvent.paymentId,orderShippedEvent.shippingId));
           	commandGateway.send(new CreateFailureInvoiceCommand(orderShippedEvent.paymentId,orderShippedEvent.orderId));
           	commandGateway.send(new CreateFailureOrderCommand(orderShippedEvent.orderId));
           	SagaLifecycle.end();
       }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderUpdatedEvent orderUpdatedEvent){
        SagaLifecycle.end();
    }
}
