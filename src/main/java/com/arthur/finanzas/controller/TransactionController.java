package com.arthur.finanzas.controller;

import com.arthur.finanzas.dto.TransactionDTO;
import com.arthur.finanzas.model.Transaction;
import com.arthur.finanzas.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions") // La URL será http://localhost:8080/api/transactions
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Permite que Angular se conecte
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO saved = transactionService.saveTransaction(transactionDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
}