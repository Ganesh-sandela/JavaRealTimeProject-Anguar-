package com.service;

import java.lang.reflect.Array;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // ✅ FIXEDimport org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Data.WattiParameter;
import com.Data.WattiRequest;
import com.Data.WattiResponse;
import com.entity.Coustmer;
import com.entity.Order;
import com.mailsender.EmailService;
import com.repo.OrderRepo;

@Service

public class NotifictionServiceImpl implements NotificationService {

	@Autowired
	private OrderRepo ordRepo;

	@Autowired
	private EmailService email;

	@Value("${wati.endpoint.url}")
	private String endpoinurl;

	@Value("${wati.api.key}")
	private String apiKey;

	@Value("${wati.template.name}")
	private String templateName;

	@Override
	@Scheduled(cron="0 7 * * * * ")		
	public Integer sendDeliveryNotification() {
		List<Order> orders = ordRepo.findByDeliveryDate(LocalDate.now());

		for (Order order : orders) {
			Coustmer coustmer = order.getCoustmer();
			sendNotificationEmail(coustmer.getEmail(), order.getOrderTrackingno());
			wattiNotification(coustmer, order.getOrderTrackingno());
		}
		return orders.size();
	}

	private WattiResponse wattiNotification(Coustmer coustmer, String orderTrackingNo) {
        
    	String apiurl=endpoinurl +"?whatsappNumber="+coustmer.getPhno();
    	
    	RestTemplate rt = new RestTemplate();
    	WattiParameter nameparam = new WattiParameter();
    	nameparam.setName("name");
    	nameparam.setValue(coustmer.getName());
    	
    	WattiParameter trackinparam = new WattiParameter();
    	trackinparam.setName("order_tracking_number");
    	trackinparam.setValue(orderTrackingNo);
    	
    	 WattiRequest request = new WattiRequest();
    	 request.setBroadcast(templateName+"_BD");
    	 request.setTemplate(templateName);
    	 request.setParam(Arrays.asList(nameparam,trackinparam));
    	 
    	 HttpHeaders headers = new HttpHeaders();
    	 headers.add("authorization",apiKey);
    	 headers.set("Content-Type", "application/json");
    	 HttpEntity<WattiRequest> entity = new HttpEntity<WattiRequest>(request, headers);
    	 try {
    		 ResponseEntity<WattiResponse> responseEntity = rt.exchange(apiurl,
    				 HttpMethod.POST,
    				 entity,
    				 WattiResponse.class
    				 );
    		 WattiResponse body = responseEntity.getBody();
    		 
    		  System.out.println("WATI Response: " + responseEntity.getBody());
    	        return body;
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		}
    }

	private boolean sendNotificationEmail(String to, String orderTrackingNo) {
		String subject = "Your Order Out for delivery";
		String body = "Your Order Tracking No: " + orderTrackingNo + " will be delivered today.";
		return email.mailsender(subject, body, to);
	}

	private boolean sendPendingdeliveryEmail(String to, String orderTrackingNo) {
		 String subject = "Order Delivery Pending Or delayed";
		    String body = "Your Order " + orderTrackingNo + " is pending and will be delivered soon.";

		    return email.mailsender(subject, body, to);
	}
	@Override
	public String sendPendingDeliveryNotification() {
	List<Order> orders = ordRepo.findByDeliveryDateAfter(LocalDate.now());
	      for(Order order:orders) {
	    	  Coustmer coustmer = order.getCoustmer();
	    	  sendPendingdeliveryEmail(coustmer.getEmail(),order.getOrderTrackingno());
	    	  wattiNotification(coustmer, order.getOrderTrackingno());
	      }
		return "pending notification sent" + orders.size();
	}

	public boolean EmailToPendingOrdersStatus(String to,String orderstrackingNo) {
		String subject="Your Orders are pending.";
		String body="Your OrderTrackingNo:"+orderstrackingNo+"Please Confirm you orders Still pending ....";
		return email.mailsender(subject, body, to);
	}
	
	@Override
	public List<Order> sendNotificationToPendingOrders() {
		List<Order> orders = ordRepo.findByOrderStatus("created");
		for(Order order:orders) {
			Coustmer coustmer = order.getCoustmer();
			EmailToPendingOrdersStatus(coustmer.getEmail(), order.getOrderTrackingno());
//			wattiNotification(coustmer, order.getOrderTrackingno());
		}
		
		return orders;
	}
	
	
}