package com.progressivecoder.ordermanagement.orderservice.services.commands;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;

public interface OrderCommandService {

    public String createOrder(OrderCreateDTO orderCreateDTO);

	public String deleteOrder(OrderDTO orderDTO);

	public String updateOrder(OrderCreateDTO orderCreateDTO);

}
