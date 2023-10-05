package com.example.madang.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.madang.model.BookEntity;
import com.example.madang.persistence.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;

	public String testService() {
		BookEntity entity = BookEntity.builder().title("My first item").build();
		repository.save(entity);
		BookEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}
	
	public List<BookEntity> create(final BookEntity entity){
		validate(entity);
		
		repository.save(entity);
		
		log.info("BookEntity Id : {} is saved", entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	
	private void validate(final BookEntity entity) {
		if(entity == null) {
			log.warn("BookEntity cannot be null");
			throw new RuntimeException("BookEntity cannot be null");
		}
		
		if(entity.getUserId() == null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}

	public List<BookEntity> retrieve(final String userId) {

		log.info("BookRetrieve", userId);

		return repository.findByUserId(userId);
	}
	
	public List<BookEntity> update(final BookEntity entity) {
		validate(entity);
		final Optional<BookEntity> original = repository.findById(entity.getId());

		original.ifPresent(b -> {
			b.setTitle(entity.getTitle());
			b.setPublisher(entity.getPublisher());
			b.setPrice(entity.getPrice());

			repository.save(b);
		});

		return retrieve(entity.getUserId());
	}
	
	public List<BookEntity> delete(final BookEntity entity){
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
