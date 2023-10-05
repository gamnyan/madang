package com.example.madang.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.madang.dto.BookDTO;
import com.example.madang.dto.ResponseDTO;
import com.example.madang.model.BookEntity;
import com.example.madang.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService service;
	
	@GetMapping
	public ResponseEntity<?> retrieve(){
		String temporaryUserId = "temporary-user";
		List<BookEntity> entities = service.retrieve(temporaryUserId);
		List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createBook(@RequestBody BookDTO dto){
		try {
			String temporaryUserId = "temporary-user";
			BookEntity entity = BookDTO.toEntity(dto);
			entity.setId(null);
			entity.setUserId(temporaryUserId);
			List<BookEntity> entities = service.create(entity);
			List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> update(/* @AuthenticationPrincipal String userId, */@RequestBody BookDTO dto){
		String temporaryUserId = "temporary-user";
		BookEntity entity = BookDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		List<BookEntity> entities = service.update(entity);
		List<BookDTO> dtos = entities
				.stream()
				.map(BookDTO::new)
				.collect(Collectors.toList());
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(/* @AuthenticationPrincipal String userId, */@RequestBody BookDTO dto){
		try {
			String temporaryUserId = "temporary-user";
			BookEntity entity = BookDTO.toEntity(dto);
			entity.setUserId(temporaryUserId);
			List<BookEntity> entities = service.delete(entity);
			List<BookDTO> dtos = entities
					.stream()
					.map(BookDTO::new)
					.collect(Collectors.toList());
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
