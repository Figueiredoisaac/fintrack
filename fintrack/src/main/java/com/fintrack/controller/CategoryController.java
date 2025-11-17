package com.fintrack.controller;

import com.fintrack.dto.CategoryDTO;
import com.fintrack.entity.Category;
import com.fintrack.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        Optional<CategoryDTO> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryDTO>> findByUser(@PathVariable Long userId) {
        List<CategoryDTO> categories = categoryService.findByUser(userId);
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<List<CategoryDTO>> findByUserAndType(
            @PathVariable Long userId, 
            @PathVariable Category.CategoryType type) {
        List<CategoryDTO> categories = categoryService.findByUserAndType(userId, type);
        return ResponseEntity.ok(categories);
    }
    
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        if (categoryService.existsByNameAndUser(categoryDTO.getName(), categoryDTO.getUserId())) {
            return ResponseEntity.badRequest().build();
        }
        
        CategoryDTO createdCategory = categoryService.create(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        Optional<CategoryDTO> updatedCategory = categoryService.update(id, categoryDTO);
        return updatedCategory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = categoryService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/types")
    public ResponseEntity<Category.CategoryType[]> getCategoryTypes() {
        return ResponseEntity.ok(Category.CategoryType.values());
    }
} 