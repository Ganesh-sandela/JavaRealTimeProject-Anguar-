package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dto.ProductCategeoryDTO;
import com.Dto.ProductDTO;
import com.Mapper.CategeoryMapper;
import com.Mapper.ProductMapper;
import com.entity.Products;
import com.repo.ProductCategeoryRepo;
import com.repo.ProductRepo;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductCategeoryRepo crepo;

	@Autowired
	private ProductRepo prepo;

	@Override
	public List<ProductCategeoryDTO> findall() {

//		ArrayList<ProductCategeoryDTO> arrayList = new ArrayList<>();
//		
//		List<ProductCategeroy> list = crepo.findAll();
//		for(ProductCategeroy l:list) {
//			ProductCategeoryDTO dto =CategeoryMapper.cvrtEntityToDto(l);
//			arrayList.add(dto);
//				}
//		return arrayList;
//		

		List<ProductCategeoryDTO> list = crepo.findAll().stream().map(CategeoryMapper::cvrtEntityToDto)
				.collect(Collectors.toList());

		return list;
	}

	@Override
	public List<ProductDTO> findByProductsCategoryId(Long CategoryId) {
		return prepo.findByCategoryId(CategoryId).stream().map(ProductMapper::cvrtEntityToDto)
				.collect(Collectors.toList());

	}

	@Override
	public ProductDTO findByProductId(Long Productid) {
		Optional<Products> byId = prepo.findById(Productid);
		if (byId.isPresent()) {
			Products products = byId.get();
			return ProductMapper.cvrtEntityToDto(products);
		}
		return null;
	}

	@Override
	public List<ProductDTO> findByProductsName(String name) {
         return prepo.findByNameContaining(name).stream()
                                          .map(ProductMapper::cvrtEntityToDto)
                                          .collect(Collectors.toList());
		

	}

}