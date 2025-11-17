package com.fintrack.repository;

import com.fintrack.entity.BankAccount;
import com.fintrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    
    List<BankAccount> findByUserAndActiveOrderByName(User user, Boolean active);
    
    @Query("SELECT ba FROM BankAccount ba WHERE ba.user = :user AND ba.active = true ORDER BY ba.name")
    List<BankAccount> findActiveByUser(@Param("user") User user);
    
    @Query("SELECT SUM(ba.currentBalance) FROM BankAccount ba WHERE ba.user = :user AND ba.active = true")
    BigDecimal getTotalBalanceByUser(@Param("user") User user);
    
    @Query("SELECT ba FROM BankAccount ba WHERE ba.user = :user AND ba.accountType = :accountType AND ba.active = true ORDER BY ba.name")
    List<BankAccount> findActiveByUserAndType(@Param("user") User user, @Param("accountType") BankAccount.AccountType accountType);
    
    boolean existsByNameAndUserAndActive(String name, User user, Boolean active);
    
    // Dashboard method
    List<BankAccount> findByUserId(Long userId);
} 