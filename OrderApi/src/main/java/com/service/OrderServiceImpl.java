package com.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.text.similarity.CosineDistance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DTO.AddressDTO;
import com.DTO.CoustmerDTO;
import com.DTO.OrderDTO;
import com.DTO.OrderItemsDto;
import com.DTO.PaymentCallBackDTO;
import com.entity.AddressEntity;
import com.entity.Coustmer;
import com.entity.Order;
import com.mailsender.EmailService;
import com.repo.AddressEntityRepo;
import com.repo.CoustmerRepo;
import com.repo.OrderItemsRepo;

import com.repo.OrderRepo;
import com.request.PurchaseOrderRequest;
import com.response.PurchaseOrderResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private AddressEntityRepo addressrepo;

	@Autowired
	private OrderRepo ordrepo;

	@Autowired
	private OrderItemsRepo orditemrepo;

	@Autowired
	private CoustmerRepo crepo;

	@Autowired
	private RazorPayService razorpayservice;

	@Autowired
	private EmailService mailsender;

	@Override
	public PurchaseOrderResponse createorder(PurchaseOrderRequest request) {

		CoustmerDTO coustmerDto = request.getCoustmer();
		AddressDTO addressDto = request.getAddressEntity();
		OrderDTO orderDto = request.getOrder();
		List<OrderItemsDto> orderItemsDtolist = request.getOrderItems();

		Coustmer c = crepo.findByEmail(coustmerDto.getEmail());

		if (c == null) {
			c = new Coustmer();
			c.setName(coustmerDto.getName());
			c.setEmail(coustmerDto.getEmail());
			c.setPhno(coustmerDto.getPhno());
			crepo.save(c);
		}

		// save address
		AddressEntity address = new AddressEntity();
		address.setHouseno(addressDto.getHouseno());
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setZipcode(addressDto.getZipcode());
		address.setCoustmer(c);
		addressrepo.save(address);

		// save order
		Order newOrder = new Order();
		String trackingNO = generateTrackingNO();
		newOrder.setOrderTrackingno(trackingNO);
		com.razorpay.Order paymentOrder = razorpayservice.createRazorpayOrder(orderDto.getTotalPrice());
		newOrder.setRazorPayOrderId(paymentOrder.get("id").toString());
		newOrder.setOrderStatus(paymentOrder.get("status").toString());
		newOrder.setTotalPrice(orderDto.getTotalPrice());
		newOrder.setTotalquantity(orderDto.getTotalquantity());
		newOrder.setEmail(c.getEmail());
		newOrder.setCoustmer(c); // associatiom mapping
		newOrder.setAddress(address); // association mapping
		ordrepo.save(newOrder);

		for (OrderItemsDto itemsDto : orderItemsDtolist) {
			com.entity.OrderItems orderitems = new com.entity.OrderItems();
			BeanUtils.copyProperties(itemsDto, orderitems);
			orderitems.setOrder(newOrder);
			orditemrepo.save(orderitems);
//			orderitems.setCoustmer(c);
		}
		// prepare and return response
		return PurchaseOrderResponse.builder().razorpayorderId(paymentOrder.get("id"))
				.orderStatus(paymentOrder.get("status")).orderTrackingNumber(trackingNO).build();
	}

	@Override
	public List<OrderDTO> getOrderByEmail(String email) {

		List<OrderDTO> dtolist = new ArrayList<>();

		List<Order> ordersList = ordrepo.findByEmail(email);

		for (Order order : ordersList) {
			OrderDTO dto = new OrderDTO();
			BeanUtils.copyProperties(order, dto);
			dtolist.add(dto);
		}

		return dtolist;
	}

	@Override
	public PurchaseOrderResponse updateOrder(PaymentCallBackDTO paymentcallbackdto) {
		Order order = ordrepo.findByRazorPayOrderId(paymentcallbackdto.getRazorPayOrderId());
		if (order != null) {
			order.setOrderStatus("CONFIRMED");
			order.setDeliverydate(LocalDate.now().plusDays(3));
			order.setRazorPayPaymentId(paymentcallbackdto.getRazorPayPaymentId());
			ordrepo.save(order);

			String Subject = "Your order is Confirmed";
			String body = "Your order will be deliverd in " + order.getDeliverydate();

			mailsender.mailsender(Subject, body, order.getEmail());
		}
		return PurchaseOrderResponse.builder().razorpayorderId(paymentcallbackdto.getRazorPayOrderId())
				.orderStatus(order.getOrderStatus()).orderTrackingNumber(order.getOrderTrackingno()).build();
	}

	private String generateTrackingNO() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStamp = sdf.format(new Date());

		String randomUUID = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
		// combine th uuid and timpStamp for orderTrackingno
		return "OD" + timeStamp + randomUUID;

	}

	@Override
	public PurchaseOrderResponse cancelOrder(Long id) {
		Order order = ordrepo.findById(id).orElseThrow(() -> new RuntimeException("order not found " + id));

		
			razorpayservice.refundPayment(order.getRazorPayPaymentId(), order.getTotalPrice());
			order.setOrderStatus("CANCELLED");

			order.setPaymentStatus("REFUND_IN_PROGRESS");
			ordrepo.save(order);

		return PurchaseOrderResponse.builder().orderStatus("CANCELLED").orderTrackingNumber(order.getOrderTrackingno())
				.razorpayorderId(order.getRazorPayOrderId()).build();
	}

}
