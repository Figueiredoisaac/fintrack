package com.fintrack.repository;

import com.fintrack.entity.Transaction;
import com.fintrack.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Page<Transaction> findByUserOrderByTransactionDateDesc(User user, Pageable pageable);
    
    List<Transaction> findByUserAndTransactionDateBetweenOrderByTransactionDateDesc(
            User user, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.transactionDate BETWEEN :startDate AND :endDate ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserAndDateRange(@Param("user") User user, 
                                           @Param("startDate") LocalDate startDate, 
                                           @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.type = :type AND t.status = 'CONFIRMED' AND t.transactionDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalByUserAndTypeAndDateRange(@Param("user") User user, 
                                               @Param("type") Transaction.TransactionType type,
                                               @Param("startDate") LocalDate startDate, 
                                               @Param("endDate") LocalDate endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.bankAccount.id = :accountId ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserAndAccount(@Param("user") User user, @Param("accountId") Long accountId);
    
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.category.id = :categoryId ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserAndCategory(@Param("user") User user, @Param("categoryId") Long categoryId);
    
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.recurring = true ORDER BY t.transactionDate DESC")
    List<Transaction> findRecurringByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.user = :user AND t.transactionDate BETWEEN :startDate AND :endDate")
    Long countByUserAndDateRange(@Param("user") User user, 
                                @Param("startDate") LocalDate startDate, 
                                @Param("endDate") LocalDate endDate);
    
    // Dashboard methods
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate AND t.status = 'CONFIRMED'")
    BigDecimal getTotalByUserAndTypeAndDateRange(@Param("userId") Long userId, @Param("type") Transaction.TransactionType type, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT t.category.id, t.category.name, SUM(t.amount), COUNT(t), t.category.type FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.transactionDate BETWEEN :startDate AND :endDate AND t.status = 'CONFIRMED' " +
           "GROUP BY t.category.id, t.category.name, t.category.type ORDER BY SUM(t.amount) DESC")
    List<Object[]> getCategoryStatsByUserAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId ORDER BY t.transactionDate DESC, t.createdAt DESC")
    List<Transaction> findRecentByUser(@Param("userId") Long userId, Pageable pageable);
    
    default List<Transaction> findRecentByUser(Long userId, int limit) {
        return findRecentByUser(userId, PageRequest.of(0, limit));
    }
} 