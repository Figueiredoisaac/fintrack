package com.fintrack.controller;

import com.fintrack.dto.TransactionDTO;
import com.fintrack.entity.Transaction;
import com.fintrack.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> findAll() {
        List<TransactionDTO> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable Long id) {
        Optional<TransactionDTO> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<TransactionDTO>> findByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionDTO> transactions = transactionService.findByUser(userId, pageable);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<TransactionDTO>> findByUserAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TransactionDTO> transactions = transactionService.findByUserAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/user/{userId}/account/{accountId}")
    public ResponseEntity<List<TransactionDTO>> findByUserAndAccount(
            @PathVariable Long userId,
            @PathVariable Long accountId) {
        List<TransactionDTO> transactions = transactionService.findByUserAndAccount(userId, accountId);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<List<TransactionDTO>> findByUserAndCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId) {
        List<TransactionDTO> transactions = transactionService.findByUserAndCategory(userId, categoryId);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/user/{userId}/recurring")
    public ResponseEntity<List<TransactionDTO>> findRecurringByUser(@PathVariable Long userId) {
        List<TransactionDTO> transactions = transactionService.findRecurringByUser(userId);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<BigDecimal> getTotalByUserAndTypeAndDateRange(
            @PathVariable Long userId,
            @RequestParam Transaction.TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal total = transactionService.getTotalByUserAndTypeAndDateRange(userId, type, startDate, endDate);
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countByUserAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long count = transactionService.countByUserAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(count);
    }
    
    @PostMapping
    public ResponseEntity<TransactionDTO> create(@Valid @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionService.create(transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@PathVariable Long id, @Valid @RequestBody TransactionDTO transactionDTO) {
        Optional<TransactionDTO> updatedTransaction = transactionService.update(id, transactionDTO);
        return updatedTransaction.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = transactionService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/types")
    public ResponseEntity<Transaction.TransactionType[]> getTransactionTypes() {
        return ResponseEntity.ok(Transaction.TransactionType.values());
    }
    
    @GetMapping("/statuses")
    public ResponseEntity<Transaction.TransactionStatus[]> getTransactionStatuses() {
        return ResponseEntity.ok(Transaction.TransactionStatus.values());
    }
} 