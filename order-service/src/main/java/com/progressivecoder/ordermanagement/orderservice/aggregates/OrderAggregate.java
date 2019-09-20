package com.progressivecoder.ordermanagement.orderservice.aggregates;

import java.math.BigDecimal;
import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.progressivecoder.ecommerce.commands.CreateOrderCommand;
import com.progressivecoder.ecommerce.commands.UpdateOrderStatusCommand;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;
import com.progressivecoder.ordermanagement.orderservice.entity.Transaction;
import com.progressivecoder.ordermanagement.orderservice.repo.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Aggregate
@Slf4j
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;

    private ItemType itemType;

    private BigDecimal price;

    private String currency;

    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @Autowired
    private TransactionRepository transactionRepository;
    
    @EventSourcingHandler
    protected void on(OrderCreatedEvent orderCreatedEvent){
        this.orderId = orderCreatedEvent.orderId;
        this.itemType = ItemType.valueOf(orderCreatedEvent.itemType);
        this.price = orderCreatedEvent.price;
        this.currency = orderCreatedEvent.currency;
        this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
    }

    @EventSourcingHandler
    protected void on(OrderUpdatedEvent orderUpdatedEvent){
        this.orderId = orderId;
        this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
    }
    
    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
    	log.info("CreateOrderCommand called successfully");
        AggregateLifecycle.apply(new OrderCreatedEvent(createOrderCommand.orderId, createOrderCommand.itemType,
                createOrderCommand.price, createOrderCommand.currency, createOrderCommand.orderStatus));
        initTxn(createOrderCommand.itemType);
        log.info("CreateOrderCommand end successfully");
    }
    
    @CommandHandler
    protected void on(UpdateOrderStatusCommand updateOrderStatusCommand){
    	log.info("UpdateOrderStatusCommand called successfully");
        AggregateLifecycle.apply(new OrderUpdatedEvent(updateOrderStatusCommand.orderId, updateOrderStatusCommand.orderStatus));
        updateTxn(orderId);
        log.info("UpdateOrderStatusCommand ends successfully");
    }
    
    public void initTxn(String item) {
    	log.info("Order Transaction Creation Initiated");
    	Transaction txn = new Transaction();
    	txn.setCurrency("INR");
    	txn.setStatus("PENDING");
    	txn.setTxnAmount(price);
    	txn.setTxnCreatedDateAt(new Date());
    	txn.setTxnProductType(item);
    	txn.setTxnUniqueId(orderId);
    	transactionRepository.save(txn);	
    	log.info("Order Transaction Creation Ends");
    }
    
    public void updateTxn(String txnUniqueId) {
    	log.info("Order Transaction Update Initiated");
    	Transaction txn = transactionRepository.findBytxnUniqueId(txnUniqueId);
    	txn.setStatus("APPROVED");
    	transactionRepository.save(txn);
    	log.info("Order Transaction Update Ends");
    }
}
