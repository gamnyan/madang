package com.example.madang.dto;

import com.example.madang.model.BookEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
	private Integer id;
	private String title;
	private String publisher;
	private String price;
	
	public BookDTO(final BookEntity book) {
		this.id = book.getId();
		this.title = book.getTitle();
		this.publisher = book.getPublisher();
		this.price = book.getPrice();
	}
	
	public static BookEntity toEntity(final BookDTO dto) {
		return BookEntity
				.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.publisher(dto.getPublisher())
				.price(dto.getPrice())
				.build();
	}
}
