package com.fintrack.service;

import com.fintrack.dto.CategoryDTO;
import com.fintrack.entity.Category;
import com.fintrack.entity.User;
import com.fintrack.repository.CategoryRepository;
import com.fintrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // CRUD Operations
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<CategoryDTO> findByUser(User user) {
        return categoryRepository.findByUserAndActive(user, true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> findByUserAndType(User user, Category.CategoryType type) {
        return categoryRepository.findByUserAndTypeAndActive(user, type, true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean existsByNameAndUser(String name, User user) {
        return categoryRepository.existsByNameAndUserAndActive(name, user, true);
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        User user = userRepository.findById(categoryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = convertToEntity(categoryDTO);
        category.setUser(user);

        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    public Optional<CategoryDTO> update(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(categoryDTO.getName());
                    existingCategory.setDescription(categoryDTO.getDescription());
                    existingCategory.setType(categoryDTO.getType());
                    existingCategory.setColor(categoryDTO.getColor());
                    existingCategory.setIcon(categoryDTO.getIcon());
                    existingCategory.setActive(categoryDTO.getActive());

                    Category savedCategory = categoryRepository.save(existingCategory);
                    return convertToDTO(savedCategory);
                });
    }

    public boolean delete(Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setActive(false);
                    categoryRepository.save(category);
                    return true;
                })
                .orElse(false);
    }

    // Conversion methods
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setType(category.getType());
        dto.setColor(category.getColor());
        dto.setIcon(category.getIcon());
        dto.setUserId(category.getUser().getId());
        dto.setActive(category.getActive());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }

    private Category convertToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setType(dto.getType());
        category.setColor(dto.getColor());
        category.setIcon(dto.getIcon());
        category.setActive(dto.getActive() != null ? dto.getActive() : true);
        return category;
    }
} 