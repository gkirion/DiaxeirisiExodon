package com.george.app.endpoint;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.george.app.entity.Category;
import com.george.app.entity.Expense;
import com.george.app.exception.CategoryNotFoundException;
import com.george.app.exception.ExpenseNotFoundException;
import com.george.app.repository.CategoryRepository;
import com.george.app.repository.ExpenseRepository;

@RestController
@RequestMapping("/expense")
@CrossOrigin
public class ExpenseRest {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@PostMapping
	public Expense add(@RequestBody Expense expense) throws CategoryNotFoundException {
		if (expense.getCategory() == null) {
			throw new CategoryNotFoundException();
		}
		Optional<Category> category = categoryRepository.findById(expense.getCategory().getId());
		if (!category.isPresent()) {
			throw new CategoryNotFoundException();
		}
		expense.setCategory(category.get());
		expense.setDate(new Date());
		return expenseRepository.save(expense);
	}
	
	@GetMapping
	public List<Expense> getAll() {
		return expenseRepository.findAll();
	}

	@GetMapping("/last10")
	public List<Expense> findFirst10OrderByDateDesc() {
		return expenseRepository.findFirst10ByOrderByDateDesc();
	}

	@GetMapping("/byDate")
	public List<Expense> findByDateBetweenOrderByDateDesc(@RequestParam("start") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date start, @RequestParam("end") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date end) {
		return expenseRepository.findByDateBetweenOrderByDateDesc(start, end);
	}

	@GetMapping("/{id}")
	public Expense get(@PathVariable Integer id) throws ExpenseNotFoundException {
		Optional<Expense> expense = expenseRepository.findById(id);
		if (!expense.isPresent()) {
			throw new ExpenseNotFoundException();
		}
		return expense.get();
	}
	
	@PutMapping("/{id}")
	public Expense update(@PathVariable Integer id, @RequestBody Expense expense) throws ExpenseNotFoundException, CategoryNotFoundException {
		Optional<Expense> oldExpense = expenseRepository.findById(id);
		if (!oldExpense.isPresent()) {
			throw new ExpenseNotFoundException();
		}
		if (expense.getCategory() == null) {
			throw new CategoryNotFoundException();
		}
		Optional<Category> category = categoryRepository.findById(expense.getCategory().getId());
		if (!category.isPresent()) {
			throw new CategoryNotFoundException();
		}
		oldExpense.get().setName(expense.getName());
		oldExpense.get().setCategory(category.get());
		oldExpense.get().setAmount(expense.getAmount());
		return expenseRepository.save(oldExpense.get());
	}
	
	@DeleteMapping("/{id}")
	public Expense delete(@PathVariable Integer id) throws ExpenseNotFoundException {
		Optional<Expense> expense = expenseRepository.findById(id);
		if (!expense.isPresent()) {
			throw new ExpenseNotFoundException();
		}
		expenseRepository.delete(expense.get());
		return expense.get();
	}

}
