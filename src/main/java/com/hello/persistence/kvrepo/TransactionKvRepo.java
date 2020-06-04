package com.hello.persistence.kvrepo;

import java.util.List;

import org.springframework.data.hazelcast.repository.HazelcastRepository;

import com.hello.persistence.entity.Transaction;

public interface TransactionKvRepo extends HazelcastRepository<Transaction, Long> {

  @Override
  List<Transaction> findAll();

  List<Transaction> findByFromAccount(String fromAccount);
}
