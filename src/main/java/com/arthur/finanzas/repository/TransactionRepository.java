package com.arthur.finanzas.repository;

import com.arthur.finanzas.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // IntelliJ te ayudará a autocompletar métodos personalizados:
    List<Transaction> findByType(String type);

    List<Transaction> findByDescriptionContainingIgnoreCase(String keyword);

}