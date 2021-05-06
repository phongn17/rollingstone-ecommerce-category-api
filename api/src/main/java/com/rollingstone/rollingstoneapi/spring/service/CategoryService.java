package com.rollingstone.rollingstoneapi.spring.service;

import com.rollingstone.rollingstoneapi.spring.model.Category;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CategoryService {
    Category save(Category category);
    Optional<Category> get(long id);
    Page<Category> getCategoriesByPage(Integer pageNumber, Integer pageSize);
    void update(long id, Category category);
    void delete(long id);
}
