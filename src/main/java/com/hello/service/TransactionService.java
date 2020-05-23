package com.hello.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import com.hello.persistence.entity.Transaction;
import com.hello.persistence.repo.TransactionRepo;

@Slf4j
@Service
public class TransactionService {
  private final TransactionRepo transactionRepo;

  public TransactionService(TransactionRepo transactionRepo) {
    this.transactionRepo = transactionRepo;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public Transaction createTransaction(String fromAc, String toAc, double amount) {
    Transaction tran = new Transaction();
    tran.setFromAccount(fromAc);
    tran.setToAccount(toAc);
    tran.setAmount(amount);
    return this.transactionRepo.save(tran);
  }

  @Transactional(readOnly = true)
  public Transaction getTransaction(long id) throws Exception {
    return this.transactionRepo.findById(id).orElseThrow(() -> new Exception("Transaction not found for id - " + id));
  }

  @Transactional(readOnly = true)
  public List<Transaction> getTransactions() {
    return this.transactionRepo.findAll();
  }
}
