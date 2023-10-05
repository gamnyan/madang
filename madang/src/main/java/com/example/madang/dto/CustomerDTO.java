package com.example.madang.dto;

import com.example.madang.model.CustomerEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {
	private Integer id;
	private String name;
	private String address;
	private String phone;
	
	public CustomerDTO(final CustomerEntity customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.address = customer.getAddress();
		this.phone = customer.getPhone();
	}
	
	public static CustomerEntity toEntity(CustomerDTO dto) {
		return CustomerEntity
				.builder()
				.id(dto.getId())
				.name(dto.getName())
				.address(dto.getAddress())
				.phone(dto.getPhone())
				.build();
	}
}
