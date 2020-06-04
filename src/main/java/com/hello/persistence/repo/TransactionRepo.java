package com.hello.persistence.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hello.persistence.entity.Transaction;

@Repository
@Transactional
public interface TransactionRepo extends CrudRepository<Transaction, Long> {

  @Query("SELECT v.transactionId FROM Transaction v")
  Iterable<Long> findAllIds();
}
