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
import com.DTO.CustomerDTO;
import com.DTO.EmailRequest;
import com.DTO.OrderDTO;
import com.DTO.OrderItemsDto;
import com.DTO.PaymentCallBackDTO;
import com.entity.Address;
import com.entity.Customer;
import com.entity.Order;
import com.entity.OrderItems;
import com.mailsender.EmailService;
import com.repo.AddressEntityRepo;
import com.repo.CoustmerRepo;
import com.repo.OrderItemsRepo;

import com.repo.OrderRepo;
import com.request.PurchaseOrderRequest;
import com.response.AdressResponse;
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

		CustomerDTO coustmerDto = request.getCustomer();
		AddressDTO addressDto = request.getAddress();
		OrderDTO orderDto = request.getOrder();
		List<OrderItemsDto> orderItemsDtolist = request.getOrderItems();

		Customer c = crepo.findByEmail(coustmerDto.getEmail());

		if (c == null) {
			c = new Customer();
			c.setName(coustmerDto.getName());
			c.setEmail(coustmerDto.getEmail());
			c.setPhno(coustmerDto.getPhno());
			crepo.save(c);
		}

		// save address
		Address address = new Address();
		address.setHouseno(addressDto.getHouseno());
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setZipcode(addressDto.getZipcode());
		address.setCustomer(c);
		addressrepo.save(address);

		// save order
		Order newOrder = new Order();
		String trackingNO = generateTrackingNO();
		newOrder.setOrderTrackingno(trackingNO);
		com.razorpay.Order paymentOrder = razorpayservice.createRazorpayOrder(orderDto.getTotalPrice());
		newOrder.setRazorPayOrderId(paymentOrder.get("id").toString());
		newOrder.setOrderStatus(paymentOrder.get("status").toString());
		newOrder.setTotalPrice(orderDto.getTotalPrice());
		newOrder.setTotalQuantity(orderDto.getTotalQuantity());
		newOrder.setEmail(c.getEmail());
		newOrder.setCustomer(c); // associatiom mapping
		newOrder.setAddress(address); // association mapping
		ordrepo.save(newOrder);

		for (OrderItemsDto itemsDto : orderItemsDtolist) {
			com.entity.OrderItems orderitems = new com.entity.OrderItems();
			BeanUtils.copyProperties(itemsDto, orderitems);
			orderitems.setOrder(newOrder);
			orderitems.setId(null);
			orderitems.setCustomer(c);
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

	public AdressResponse getAllDetails(String email) {
		  Customer c = crepo.findByEmail(email);
		  if (c == null) {
		        return null; 
		    }

		  
		  List<Address> address = addressrepo.findByCustomerId(c.getId());
		  
		  List<Order> listorder= new ArrayList<>();
		  for( Address add:address) {
			    List<Order> order = ordrepo.findByAddressId(add.getId());
			    listorder.addAll(order);
		  }
		  List<OrderItems> items=new ArrayList<>();
		  for(Order ord:listorder) {
			   List<OrderItems> byOrderIdItems = orditemrepo.findByOrderId(ord.getId());
			   items.addAll(byOrderIdItems);
		  }
		
	return AdressResponse.builder()
			             .customer(c)
			             .address(address)
			             .order(listorder)
			             .orderItems(items)
			             .build();
	}
}
