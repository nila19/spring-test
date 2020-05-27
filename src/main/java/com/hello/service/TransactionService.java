package com.hello.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.hello.persistence.entity.Transaction;
import com.hello.persistence.repo.TransactionRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
  private final TransactionRepo transactionRepo;

  @Transactional(propagation = Propagation.REQUIRED)
  public Transaction createTransaction(String fromAc, String toAc, double amount) {
    return this.transactionRepo.save(new Transaction(fromAc, toAc, amount));
  }

  @Transactional(readOnly = true)
  public Transaction getTransaction(long id) {
    return this.transactionRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Transaction not found for id - " + id));
  }

  @Transactional(readOnly = true)
  public List<Transaction> getTransactions() {
    return this.transactionRepo.findAll();
  }
}
