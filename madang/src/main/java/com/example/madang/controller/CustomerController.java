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

import com.example.madang.dto.CustomerDTO;
import com.example.madang.dto.ResponseDTO;
import com.example.madang.model.CustomerEntity;
import com.example.madang.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@GetMapping
	public ResponseEntity<?> retrieve(){
		String temporaryUserId = "temporary-user";
		List<CustomerEntity> entities = service.retrieve(temporaryUserId);
		List<CustomerDTO> dtos = entities.stream().map(CustomerDTO::new).collect(Collectors.toList());
		ResponseDTO<CustomerDTO> response = ResponseDTO.<CustomerDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO dto){
		try {
			String temporaryUserId = "temporary-user";
			CustomerEntity entity = CustomerDTO.toEntity(dto);
			entity.setId(null);
			entity.setUserId(temporaryUserId);
			List<CustomerEntity> entities = service.create(entity);
			List<CustomerDTO> dtos = entities.stream().map(CustomerDTO::new).collect(Collectors.toList());
			ResponseDTO<CustomerDTO> response = ResponseDTO.<CustomerDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<CustomerDTO> response = ResponseDTO.<CustomerDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> update(/* @AuthenticationPrincipal String userId, */@RequestBody CustomerDTO dto){
		String temporaryUserId = "temporary-user";
		CustomerEntity entity = CustomerDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		List<CustomerEntity> entities = service.update(entity);
		List<CustomerDTO> dtos = entities
				.stream()
				.map(CustomerDTO::new)
				.collect(Collectors.toList());
		ResponseDTO<CustomerDTO> response = ResponseDTO.<CustomerDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(/* @AuthenticationPrincipal String userId, */@RequestBody CustomerDTO dto){
		try {
			String temporaryUserId = "temporary-user";
			CustomerEntity entity = CustomerDTO.toEntity(dto);
			entity.setUserId(temporaryUserId);
			List<CustomerEntity> entities = service.delete(entity);
			List<CustomerDTO> dtos = entities
					.stream()
					.map(CustomerDTO::new)
					.collect(Collectors.toList());
			ResponseDTO<CustomerDTO> response = ResponseDTO.<CustomerDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<CustomerDTO> response = ResponseDTO.<CustomerDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
