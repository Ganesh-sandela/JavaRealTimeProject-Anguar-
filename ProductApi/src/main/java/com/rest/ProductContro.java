package com.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Dto.ProductCategeoryDTO;
import com.Dto.ProductDTO;
import com.Responseapi.ResponseApi;
import com.service.ProductService;
import com.service.ProductServiceImpl;

@RestController
@CrossOrigin(origins = "*")
public class ProductContro {

	@Autowired
	private ProductService pserv;

	@GetMapping("/categories")
	public ResponseEntity<ResponseApi<List<ProductCategeoryDTO>>> productCategeories() {
		ResponseApi<List<ProductCategeoryDTO>> response = new ResponseApi<>();
		List<ProductCategeoryDTO> data = pserv.findall();
		System.out.println(data);
		if (data != null) {
			response.setStatus(200);
			response.setMsg("fetched categeories succesfully");
			response.setData(data);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMsg("Failed to fetch categories");
			response.setData(null); // no payload
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("category/{categoreyId}")
	public ResponseEntity<ResponseApi<List<ProductDTO>>> products(@PathVariable Long categoreyId){
		 
		ResponseApi<List<ProductDTO>> response = new ResponseApi<>();
		List<ProductDTO> clist = pserv.findByProductsCategoryId(categoreyId);
		if (clist!=null) {
			response.setStatus(200);
			response.setMsg("fetched categeories succesfully");
			response.setData(clist);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		else {
			response.setStatus(500);
			response.setMsg("Failed to fetch categories");
			response.setData(null); // no payload
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("product/{productId}")
	public ResponseEntity<ResponseApi<ProductDTO>> productbyid(@PathVariable Long productId){
		 ResponseApi<ProductDTO> response = new ResponseApi<>();
	 ProductDTO product = pserv.findByProductId(productId);
	 
	 if (product!=null) {
		 response.setStatus(200);
			response.setMsg("fetched products succesfully");
			response.setData(product);
			return new ResponseEntity<>(response, HttpStatus.OK);
	}
	 else {
		 response.setStatus(500);
			response.setMsg("Failed to fetch product");
			response.setData(null); // no payload
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	}
	
	@GetMapping("/productsByName/{productName}")
	public ResponseEntity<ResponseApi<List<ProductDTO>>> getbyproductsname(@PathVariable String productName){
		 ResponseApi<List<ProductDTO>> response = new ResponseApi<>();
		 List<ProductDTO> product = pserv.findByProductsName(productName);

		 if (!product.isEmpty()) {
				response.setStatus(200);
				response.setMsg("Fetched Products Succesfully");
				response.setData(product);
				return new ResponseEntity<>(response, HttpStatus.OK);
	}else {
		response.setStatus(500);
		response.setMsg("Failed to fetch product");
		response.setData(null); // no payload
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

}