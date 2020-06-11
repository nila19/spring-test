package com.springtest.spring.persistence.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springtest.spring.persistence.entity.Transaction;

@Repository
@Transactional
public interface TransactionRepo extends CrudRepository<Transaction, Long> {

  @Override
  List<Transaction> findAll();

  List<Transaction> findByFromAccount(String fromAccount);
}
