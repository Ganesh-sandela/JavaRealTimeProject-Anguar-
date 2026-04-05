package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Data.WattiResponse;
import com.entity.Order;
@Service
public interface NotificationService {

	public Integer sendDeliveryNotification();
	
	public String sendPendingDeliveryNotification();
	
	public List<Order> sendNotificationToPendingOrders();
}
