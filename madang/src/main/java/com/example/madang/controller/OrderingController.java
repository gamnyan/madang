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

import com.example.madang.dto.OrderingDTO;
import com.example.madang.dto.ResponseDTO;
import com.example.madang.model.OrderingEntity;
import com.example.madang.service.OrderingService;

@RestController
@RequestMapping("/ordering")
public class OrderingController {

	@Autowired
	private OrderingService service;
	
	@GetMapping
	public ResponseEntity<?> retrieve(){
		String temporaryUserId = "temporary-user";
		List<OrderingEntity> entities = service.retrieve(temporaryUserId);
		List<OrderingDTO> dtos = entities.stream().map(OrderingDTO::new).collect(Collectors.toList());
		ResponseDTO<OrderingDTO> response = ResponseDTO.<OrderingDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody OrderingDTO dto){
		try {
			String temporaryUserId = "temporary-user";
			OrderingEntity entity = OrderingDTO.toEntity(dto);
			entity.setId(null);
			entity.setUserId(temporaryUserId);
			List<OrderingEntity> entities = service.create(entity);
			List<OrderingDTO> dtos = entities.stream().map(OrderingDTO::new).collect(Collectors.toList());
			ResponseDTO<OrderingDTO> response = ResponseDTO.<OrderingDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<OrderingDTO> response = ResponseDTO.<OrderingDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> update(/* @AuthenticationPrincipal String userId, */@RequestBody OrderingDTO dto){
		String temporaryUserId = "temporary-user";
		OrderingEntity entity = OrderingDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		List<OrderingEntity> entities = service.update(entity);
		List<OrderingDTO> dtos = entities
				.stream()
				.map(OrderingDTO::new)
				.collect(Collectors.toList());
		ResponseDTO<OrderingDTO> response = ResponseDTO.<OrderingDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(/* @AuthenticationPrincipal String userId, */@RequestBody OrderingDTO dto){
		try {
			String temporaryUserId = "temporary-user";
			OrderingEntity entity = OrderingDTO.toEntity(dto);
			entity.setUserId(temporaryUserId);
			List<OrderingEntity> entities = service.delete(entity);
			List<OrderingDTO> dtos = entities
					.stream()
					.map(OrderingDTO::new)
					.collect(Collectors.toList());
			ResponseDTO<OrderingDTO> response = ResponseDTO.<OrderingDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<OrderingDTO> response = ResponseDTO.<OrderingDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
