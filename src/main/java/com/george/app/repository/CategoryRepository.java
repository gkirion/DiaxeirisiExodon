package com.george.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.george.app.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
