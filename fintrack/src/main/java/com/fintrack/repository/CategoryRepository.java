package com.fintrack.repository;

import com.fintrack.entity.Category;
import com.fintrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    List<Category> findByUserAndActiveOrderByName(User user, Boolean active);
    
    List<Category> findByUserAndTypeAndActiveOrderByName(User user, Category.CategoryType type, Boolean active);
    
    @Query("SELECT c FROM Category c WHERE c.user = :user AND c.active = true ORDER BY c.name")
    List<Category> findActiveByUser(@Param("user") User user);
    
    @Query("SELECT c FROM Category c WHERE c.user = :user AND c.type = :type AND c.active = true ORDER BY c.name")
    List<Category> findActiveByUserAndType(@Param("user") User user, @Param("type") Category.CategoryType type);
    
    boolean existsByNameAndUserAndActive(String name, User user, Boolean active);
} 