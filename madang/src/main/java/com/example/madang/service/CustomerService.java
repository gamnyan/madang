package com.example.madang.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.madang.model.CustomerEntity;
import com.example.madang.persistence.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository repository;

	public String testService() {
		CustomerEntity entity = CustomerEntity.builder().address("My first item").build();
		repository.save(entity);
		CustomerEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getAddress();
	}
	
	public List<CustomerEntity> create(final CustomerEntity entity){
		validate(entity);
		
		repository.save(entity);
		
		log.info("CustomerEntity Id : {} is saved", entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	
	private void validate(final CustomerEntity entity) {
		if(entity == null) {
			log.warn("CustomerEntity cannot be null");
			throw new RuntimeException("CustomerEntity cannot be null");
		}
		
		if(entity.getUserId() == null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}

	public List<CustomerEntity> retrieve(final String userId) {

		log.info("CustomerRetrieve", userId);

		return repository.findByUserId(userId);
	}
	
	public List<CustomerEntity> update(final CustomerEntity entity) {
		validate(entity);
		final Optional<CustomerEntity> original = repository.findById(entity.getId());

		original.ifPresent(c -> {
			c.setName(entity.getName());
			c.setAddress(entity.getAddress());
			c.setPhone(entity.getPhone());

			repository.save(c);
		});

		return retrieve(entity.getUserId());
	}
	
	public List<CustomerEntity> delete(final CustomerEntity entity){
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
