package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.NotificationApiApplication;
import com.entity.Order;
import com.service.NotifictionServiceImpl;

@RestController
public class NotificationRestContro  {



	@Autowired
	private NotifictionServiceImpl ntserv;

   
@GetMapping("/order-notifications")
public String sendNotification() {
	List<Order> notification = ntserv.sendNotificationToPendingOrders();
	return " succes..........."+ notification;
}
}
