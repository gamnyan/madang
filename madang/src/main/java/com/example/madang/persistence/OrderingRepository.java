package com.example.madang.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.madang.model.OrderingEntity;

@Repository
public interface OrderingRepository extends JpaRepository<OrderingEntity, Integer> {
	List<OrderingEntity> findByUserId(String userId);
}
