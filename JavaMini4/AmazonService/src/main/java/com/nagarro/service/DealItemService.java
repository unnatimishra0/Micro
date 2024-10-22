package com.nagarro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.model.DealItem;
import com.nagarro.repository.DealItemRepository;

@Service
public class DealItemService {
	
	private DealItemRepository repository;
	@Autowired
	public DealItemService(DealItemRepository repository) {
		this.repository=repository;
	}
	
	public  List<DealItem> findByCategoryName(String categoryName){
		return repository.findByCategoryName(categoryName);
	}

}
