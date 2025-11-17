package com.fintrack.controller;

import com.fintrack.dto.BankAccountDTO;
import com.fintrack.entity.BankAccount;
import com.fintrack.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {
    
    @Autowired
    private BankAccountService bankAccountService;
    
    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> findAll() {
        List<BankAccountDTO> accounts = bankAccountService.findAll();
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> findById(@PathVariable Long id) {
        Optional<BankAccountDTO> account = bankAccountService.findById(id);
        return account.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BankAccountDTO>> findByUser(@PathVariable Long userId) {
        List<BankAccountDTO> accounts = bankAccountService.findByUser(userId);
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/user/{userId}/type/{accountType}")
    public ResponseEntity<List<BankAccountDTO>> findByUserAndType(
            @PathVariable Long userId, 
            @PathVariable BankAccount.AccountType accountType) {
        List<BankAccountDTO> accounts = bankAccountService.findByUserAndType(userId, accountType);
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<BigDecimal> getTotalBalanceByUser(@PathVariable Long userId) {
        BigDecimal totalBalance = bankAccountService.getTotalBalanceByUser(userId);
        return ResponseEntity.ok(totalBalance);
    }
    
    @PostMapping
    public ResponseEntity<BankAccountDTO> create(@Valid @RequestBody BankAccountDTO bankAccountDTO) {
        if (bankAccountService.existsByNameAndUser(bankAccountDTO.getName(), bankAccountDTO.getUserId())) {
            return ResponseEntity.badRequest().build();
        }
        
        BankAccountDTO createdAccount = bankAccountService.create(bankAccountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDTO> update(@PathVariable Long id, @Valid @RequestBody BankAccountDTO bankAccountDTO) {
        Optional<BankAccountDTO> updatedAccount = bankAccountService.update(id, bankAccountDTO);
        return updatedAccount.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = bankAccountService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/types")
    public ResponseEntity<BankAccount.AccountType[]> getAccountTypes() {
        return ResponseEntity.ok(BankAccount.AccountType.values());
    }
} 