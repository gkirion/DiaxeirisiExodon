package com.george.app.endpoint;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.george.app.entity.Category;
import com.george.app.entity.Expense;
import com.george.app.exception.CategoryNotFoundException;
import com.george.app.repository.CategoryRepository;
import com.george.app.repository.ExpenseRepository;

@RestController
@RequestMapping("/category")
public class CategoryRest {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@PostMapping
	public Category add(@RequestBody Category category) {
		return categoryRepository.save(category);
	}
	
	@GetMapping
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}
	
	@PutMapping("/{id}")
	public Category update(@PathVariable Integer id, @RequestBody Category updatedCategory) throws CategoryNotFoundException {
		Optional<Category> oldCategory = categoryRepository.findById(id);
		if (!oldCategory.isPresent()) {
			throw new CategoryNotFoundException();
		}
		oldCategory.get().setName(updatedCategory.getName());
		return categoryRepository.save(oldCategory.get());
	}
	
	@GetMapping("/{id}")
	public Category get(@PathVariable Integer id) throws CategoryNotFoundException {
		Optional<Category> category = categoryRepository.findById(id);
		if (!category.isPresent()) {
			throw new CategoryNotFoundException();
		}
		return category.get();
	}
	
	@DeleteMapping("/{id}")
	public Category delete(@PathVariable Integer id) throws CategoryNotFoundException {
		Optional<Category> category = categoryRepository.findById(id);
		if (!category.isPresent()) {
			throw new CategoryNotFoundException();
		}
		List<Expense> expenses = expenseRepository.findByCategoryId(id);
		for (Expense expense : expenses) {
			expense.setCategory(null);
		}
		categoryRepository.delete(category.get());
		return category.get();
	}

}
