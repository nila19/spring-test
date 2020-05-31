package com.hello.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hello.exception.HelloException;
import com.hello.persistence.entity.Transaction;
import com.hello.persistence.mapper.TransactionMapper;
import com.hello.persistence.model.TransactionDTO;
import com.hello.persistence.repo.TransactionRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
  private final TransactionRepo transactionRepo;
  private final TransactionMapper transactionMapper;

  @Transactional(propagation = Propagation.REQUIRED)
  public TransactionDTO createTransaction(TransactionDTO dto) {
    Validate.isTrue(StringUtils.isNotBlank(dto.getFromAccount()), "FromAc cannot be empty.");
    Validate.isTrue(StringUtils.isNotBlank(dto.getToAccount()), "ToAc cannot be empty.");
    Validate.isTrue(dto.getAmount() != 0, "Amount cannot be zero.");
    Transaction tran = this.transactionRepo.save(this.transactionMapper.toEntity(dto));
    return this.transactionMapper.toDto(tran);
  }

  @Cacheable(value = "tran", key = "#id")
  @Transactional(readOnly = true)
  public TransactionDTO getTransaction(long id) {
    this.slowItDown();
    Transaction tran = this.transactionRepo.findById(id)
        .orElseThrow(() -> new HelloException("Transaction not found for id - " + id));
    return this.transactionMapper.toDto(tran);
  }

  // Don't do this at home
  private void slowItDown() {
    try {
      long time = 1000L;
      Thread.sleep(time);
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }

  @Cacheable(value = "trans")
  @Transactional(readOnly = true)
  public List<TransactionDTO> getTransactions() {
    this.slowItDown();
    return this.transactionRepo.findAll().stream()
        .map(this.transactionMapper::toDto)
        .collect(Collectors.toList());
  }
}
