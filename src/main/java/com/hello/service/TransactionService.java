package com.hello.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.hello.exception.HelloException;
import com.hello.persistence.entity.Transaction;
import com.hello.persistence.repo.TransactionRepo;

@Slf4j
@Service
public class TransactionService {
  private final TransactionRepo transactionRepo;

  public TransactionService(TransactionRepo transactionRepo) {
    this.transactionRepo = transactionRepo;
  }

  public Iterable<Long> findAllIds() {
    return this.transactionRepo.findAllIds();
  }

  public Transaction findById(long id) {
    return this.transactionRepo.findById(id)
        .orElseThrow(() -> new HelloException("Transaction not found for id - " + id));
  }
}
