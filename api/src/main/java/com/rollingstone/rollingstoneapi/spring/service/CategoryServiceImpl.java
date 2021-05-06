package com.rollingstone.rollingstoneapi.spring.service;

import com.rollingstone.rollingstoneapi.exceptions.HTTP400Exception;
import com.rollingstone.rollingstoneapi.spring.dao.CategoryDaoRepository;
import com.rollingstone.rollingstoneapi.spring.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDaoRepository dao;

    @Override
    public Category save(Category category) {
        try {
            return dao.save(category);
        } catch (Exception ex) {
            throw new HTTP400Exception(ex.getMessage());
        }
    }

    @Override
    public Optional<Category> get(long id) {
        return dao.findById(id);
    }

    @Override
    public Page<Category> getCategoriesByPage(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("categoryName").descending());

        return dao.findAll(pageable);
    }

    @Override
    public void update(long id, Category category) {
        dao.save(category);
    }

    @Override
    public void delete(long id) {
        dao.deleteById(id);
    }
}
