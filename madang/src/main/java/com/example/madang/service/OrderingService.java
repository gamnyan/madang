package com.example.madang.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.madang.model.OrderingEntity;
import com.example.madang.persistence.OrderingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderingService {
	
	@Autowired
	private OrderingRepository repository;

	public String testService() {
		OrderingEntity entity = OrderingEntity.builder().sellingPrice("My first item").build();
		repository.save(entity);
		OrderingEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getSellingPrice();
	}
	
	public List<OrderingEntity> create(final OrderingEntity entity){
		validate(entity);
		
		repository.save(entity);
		
		log.info("OrderingEntity Id : {} is saved", entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	
	private void validate(final OrderingEntity entity) {
		if(entity == null) {
			log.warn("OrderingEntity cannot be null");
			throw new RuntimeException("OrderingEntity cannot be null");
		}
		
		if(entity.getUserId() == null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}

	public List<OrderingEntity> retrieve(final String userId) {

		log.info("OrderingRetrieve", userId);

		return repository.findByUserId(userId);
	}
	
	public List<OrderingEntity> update(final OrderingEntity entity) {
		validate(entity);
		final Optional<OrderingEntity> original = repository.findById(entity.getId());

		original.ifPresent(o -> {
			o.setSellingPrice(entity.getSellingPrice());
			o.setOrderingDate(entity.getOrderingDate());
			o.setCustomerId(entity.getCustomerId());
			o.setBookId(entity.getBookId());

			repository.save(o);
		});

		return retrieve(entity.getUserId());
	}
	
	public List<OrderingEntity> delete(final OrderingEntity entity){
		validate(entity);
		
		try {
			repository.delete(entity);
		} catch(Exception e	) {
			log.error("error deleting entity ", entity.getId(), e);
			
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		return retrieve(entity.getUserId());
	}
}
