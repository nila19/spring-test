package com.hello.persistence.kvloader;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.hazelcast.core.MapLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.hello.exception.HelloException;
import com.hello.persistence.entity.Transaction;
import com.hello.persistence.repo.TransactionRepo;

@Slf4j
@Component
public class TransactionKvLoader implements MapLoader<Long, Transaction> {

  private final TransactionRepo transactionRepo;

  public TransactionKvLoader(TransactionRepo transactionRepo) {
    this.transactionRepo = transactionRepo;
  }

  @Override
  public Transaction load(Long key) {
    return this.transactionRepo.findById(key)
        .orElseThrow(() -> new HelloException("Transaction not found for id - " + key));
  }

  @Override
  public Map<Long, Transaction> loadAll(Collection<Long> keys) {
    return keys.stream()
        .map(this::load)
        .collect(Collectors.toMap(Transaction::getTransactionId, e -> e));
  }

  @Override
  public Iterable<Long> loadAllKeys() {
    return this.transactionRepo.findAllIds();
  }
}
