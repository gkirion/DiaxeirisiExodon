package com.george.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.george.app.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
	
	public List<Expense> findByCategoryId(Integer categoryId);

}
