package com.george.app.endpoint;

import java.util.List;
import java.util.Optional;

import com.george.app.exception.CategoryAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class CategoryRest {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ExpenseRepository expenseRepository;
	
	@PostMapping
	public Category add(@RequestBody Category category) throws CategoryAlreadyExistsException {
		List<Category> existingCategories = categoryRepository.findByName(category.getName());
		if (!existingCategories.isEmpty()) {
			throw new CategoryAlreadyExistsException();
		}
		return categoryRepository.save(category);

	}
	
	@GetMapping
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}
	
	@PutMapping("/{id}")
	public Category update(@PathVariable Integer id, @RequestBody Category updatedCategory) throws CategoryNotFoundException, CategoryAlreadyExistsException {
		Optional<Category> oldCategory = categoryRepository.findById(id);
		if (!oldCategory.isPresent()) {
			throw new CategoryNotFoundException();
		}
		List<Category> existingCategories = categoryRepository.findByName(updatedCategory.getName());
		if (!existingCategories.isEmpty()) {
			if (existingCategories.get(0).getId() != oldCategory.get().getId()) {
				throw new CategoryAlreadyExistsException();
			}
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
