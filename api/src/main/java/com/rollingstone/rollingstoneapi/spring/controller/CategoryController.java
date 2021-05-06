package com.rollingstone.rollingstoneapi.spring.controller;

import com.rollingstone.rollingstoneapi.events.CategoryEvent;
import com.rollingstone.rollingstoneapi.spring.model.Category;
import com.rollingstone.rollingstoneapi.spring.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CategoryController extends AbstractController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /*
        @RequestBody are standard Spring Boot Web framework annotations to deal with
        the response Json serialization and deserialization by jackson library we included in our build.gradle file.
     */
    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        CategoryEvent evt = new CategoryEvent("One category is created", savedCategory);
        eventPublisher.publishEvent(evt);

        return ResponseEntity.ok().body("New category has been saved with the Id: " + savedCategory.getId());
    }

    @GetMapping("/category/{id}")
    @ResponseBody
    public Category getCategory(@PathVariable("id") long id) {
        Optional<Category> category = categoryService.get(id);
        Category cat = category.get();

        CategoryEvent evt = new CategoryEvent("One category is retrieved", cat);
        eventPublisher.publishEvent(evt);

        return cat;
    }

    @GetMapping("/category")
    @ResponseBody
    public Page<Category> getCategoriesByPage(@RequestParam(value = "pagenumber", required = true, defaultValue = "0")Integer pageNumber,
        @RequestParam(value = "pagesize", required = true, defaultValue = "20")Integer pageSize
    ) {
        Page<Category> pagedCats = categoryService.getCategoriesByPage(pageNumber, pageSize);

        return pagedCats;
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id")long id, @RequestBody Category category) {
        checkResourceFound(this.categoryService.get(id));
        categoryService.update(id, category);

        return ResponseEntity.ok().body("Category has been updated successfully.");
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id")long id) {
        checkResourceFound(categoryService.get(id));

        categoryService.delete(id);

        return ResponseEntity.ok().body("Category has been deleted successfully");
    }
}
