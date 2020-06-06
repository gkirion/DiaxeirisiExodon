package com.george.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.george.app.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public List<Category> findByName(String name);

}
