package com.progressivecoder.shippingmanagement.shippingservice.services.commands;

import com.progressivecoder.ecommerce.dto.OrderCreateDTO;
import com.progressivecoder.ecommerce.dto.OrderDTO;

public interface ShipService {

    public String createOrder(OrderCreateDTO orderCreateDTO);

	public String deleteOrder(OrderDTO orderDTO);

}
