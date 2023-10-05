package com.example.madang.dto;

import com.example.madang.model.OrderingEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderingDTO {
	private Integer id;
	private String sellingPrice;
	private String orderingDate;
	private String customerId;
	private String bookId;
	
	public OrderingDTO(final OrderingEntity ordering) {
		this.id = ordering.getId();
		this.sellingPrice = ordering.getSellingPrice();
		this.orderingDate = ordering.getOrderingDate();
		this.customerId = ordering.getCustomerId();
		this.bookId = ordering.getBookId();
	}
	
	public static OrderingEntity toEntity(final OrderingDTO dto) {
		return OrderingEntity
				.builder()
				.id(dto.getId())
				.sellingPrice(dto.getSellingPrice())
				.orderingDate(dto.orderingDate)
				.customerId(dto.getCustomerId())
				.bookId(dto.getBookId())
				.build();
	}
}
