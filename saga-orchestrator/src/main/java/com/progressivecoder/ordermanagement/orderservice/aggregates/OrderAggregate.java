package com.progressivecoder.ordermanagement.orderservice.aggregates;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.progressivecoder.ecommerce.commands.CreateFailureOrderCommand;
import com.progressivecoder.ecommerce.commands.CreateOrderCommand;
import com.progressivecoder.ecommerce.commands.UpdateOrderStatusCommand;
import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.events.OrderCreatedErrorEvent;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;
import com.progressivecoder.ordermanagement.orderservice.constants.ItemType;
import com.progressivecoder.ordermanagement.orderservice.constants.OrderStatus;

@Component
@Aggregate
public class OrderAggregate {

	
	private Logger log  = Logger.getLogger(OrderAggregate.class.getName());
	
	
    @AggregateIdentifier
    private String orderId;

    private ItemType itemType;

    private BigDecimal price;

    private String currency;

    private OrderStatus orderStatus;

    public OrderAggregate() {
    
    }

   
    @EventSourcingHandler
    protected void on(OrderCreatedEvent orderCreatedEvent){
        this.orderId = orderCreatedEvent.orderId;
        this.itemType = ItemType.valueOf(orderCreatedEvent.itemType);
        this.price = orderCreatedEvent.price;
        this.currency = orderCreatedEvent.currency;
        this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
    }
 
    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) throws RestClientException, Exception{
    	log.info("CreateOrderCommand called successfully");
        AggregateLifecycle.apply(new OrderCreatedEvent(createOrderCommand.orderId, createOrderCommand.itemType,
                createOrderCommand.price, createOrderCommand.currency, createOrderCommand.orderStatus));
        //rest call request to create
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        orderCreateDTO.setCurrency(createOrderCommand.currency);
        orderCreateDTO.setItemType(createOrderCommand.itemType);
        orderCreateDTO.setOrderId(createOrderCommand.orderId);
        orderCreateDTO.setPrice(createOrderCommand.price);
        orderCreateDTO.setRequestedBy("ADMIN");
        log.info("CreateOrderCommand Request :: " + orderCreateDTO);
        //rest api call
        ResponseEntity<?> responseEntity =  this.invokeAPI("http://localhost:8081/api/orders",
				HttpMethod.POST,orderCreateDTO, String.class);
        String mdmMesponse =  (String) responseEntity.getBody();
		log.info("CreateOrderCommand Response :: " + mdmMesponse);
        log.info("CreateOrderCommand end successfully");
    }
    
    @EventSourcingHandler
    protected void on(OrderCreatedErrorEvent orderCreatedErrorEvent){
        this.orderId = orderCreatedErrorEvent.orderId;
    }
 
    @CommandHandler
    protected void on(CreateFailureOrderCommand createFailureOrderCommand) throws RestClientException, Exception{
		log.info("CreateFailureOrderCommand called successfully");
		AggregateLifecycle.apply(new CreateFailureOrderCommand(createFailureOrderCommand.orderId));
		log.info("Invoke OrderFailure Service begins");
		OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
		orderCreateDTO.setOrderId(createFailureOrderCommand.orderId);
		orderCreateDTO.setRequestedBy("ADMIN");
		log.info("CreateFailureOrderCommand Request :: " + orderCreateDTO);
		ResponseEntity<?> responseEntity =  this.invokeAPI("http://localhost:8081/api/orders",HttpMethod.DELETE,orderCreateDTO, String.class);
		log.info("Invoke OrderFailure Service ends");
        String mdmMesponse =  (String) responseEntity.getBody();
		log.info("CreateFailureOrderCommand Response :: " + mdmMesponse);
		log.info("OrderCreatedErrorEvent end successfully");
    }
    
    
    @EventSourcingHandler
    protected void on(OrderUpdatedEvent orderUpdatedEvent){
        this.orderId = orderId;
        this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
    }
    
    @CommandHandler
    protected void on(UpdateOrderStatusCommand updateOrderStatusCommand){
    	log.info("UpdateOrderStatusCommand called successfully");
        AggregateLifecycle.apply(new OrderUpdatedEvent(updateOrderStatusCommand.orderId, updateOrderStatusCommand.orderStatus));
        //rest call to create
        log.info("UpdateOrderStatusCommand ends successfully");
    }
   
    
    //Generic method to call lifafa apis
  	private ResponseEntity<?> invokeAPI(String url,HttpMethod method,Object payload,Class<?> responseType) throws RestClientException, Exception{
  		ResponseEntity<?> response  = null;
  		HttpEntity<?> requestEntity = new HttpEntity<>(payload);
  		RestTemplate restTemplate = new RestTemplate(getRequestFactory());
  		response = restTemplate.exchange(url,method,requestEntity,responseType);
        return response;
  	}
    
    private final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(1000);
    
    private ClientHttpRequestFactory getRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(TIMEOUT);
        factory.setConnectTimeout(TIMEOUT);
        factory.setConnectionRequestTimeout(TIMEOUT);
        return factory;
    }
 
}
