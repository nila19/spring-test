package com.hello.persistence.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hello.persistence.entity.Transaction;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {
  @Override
  List<Transaction> findAll();
}
