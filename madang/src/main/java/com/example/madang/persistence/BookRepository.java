package com.example.madang.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.madang.model.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
	List<BookEntity> findByUserId(String userId);
}
