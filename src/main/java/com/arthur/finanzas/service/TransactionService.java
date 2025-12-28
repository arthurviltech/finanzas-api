package com.arthur.finanzas.service;

import com.arthur.finanzas.dto.TransactionDTO;
import com.arthur.finanzas.exception.ResourceNotFoundException;
import com.arthur.finanzas.model.Transaction;
import com.arthur.finanzas.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Genera el constructor para inyectar el repository automáticamente
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper; // Inyéctalo en el constructor

    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con ID: " + id));
        return modelMapper.map(transaction, TransactionDTO.class);
    }
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        // 1. Convertir DTO -> Entidad
        Transaction entity = modelMapper.map(transactionDTO, Transaction.class);

        // 2. Guardar en SQLite
        Transaction savedEntity = transactionRepository.save(entity);

        // 3. Convertir Entidad guardada -> DTO (para devolver el ID generado)
        return modelMapper.map(savedEntity, TransactionDTO.class);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}