package com.hello.persistence.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hello.persistence.entity.Transaction;

@Repository
@Transactional
public interface TransactionRepo extends CrudRepository<Transaction, Long> {
  @Override
  List<Transaction> findAll();

  List<Transaction> findByFromAccount(String fromAccount);
}
