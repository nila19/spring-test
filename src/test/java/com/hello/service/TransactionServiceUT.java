package com.hello.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hello.persistence.entity.Transaction;
import com.hello.persistence.repo.TransactionRepo;
import com.hello.util.TransactionMatcher;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest
//@AutoConfigureMockMvc
public class TransactionServiceUT {
  private static final Transaction t1 = new Transaction(100, "P1", "T1", 1000);
  private static final Transaction t2 = new Transaction(101, "P2", "T2", 2000);
  private static final Transaction t3 = new Transaction(102, "P3", "T3", 3000);

  @Mock
  private TransactionRepo transactionRepo;

  @InjectMocks
  private TransactionService transactionService;

  @BeforeEach
  public void setUp() {
    //    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getTransaction_whenNotFound() {
    long id = t1.getTransactionId();
    Mockito.when(this.transactionRepo.findById(id)).thenReturn(Optional.empty());

    Exception ex =
        assertThrows(RuntimeException.class, () -> this.transactionService.getTransaction(id));
    String msg = "Transaction not found for id - " + id;
    assertTrue(StringUtils.equalsIgnoreCase(msg, ex.getMessage()));
    Mockito.verify(this.transactionRepo, Mockito.times(1)).findById(id);
  }

  @Test
  public void getTransaction_whenFound() {
    long id = t1.getTransactionId();
    Mockito.when(this.transactionRepo.findById(id)).thenReturn(Optional.of(t1));

    assertThat(this.transactionService.getTransaction(id), is(t1));
    Mockito.verify(this.transactionRepo, Mockito.times(1)).findById(id);
  }

  @Test
  public void getTransactions_whenNoRecords() {
    Mockito.when(this.transactionRepo.findAll()).thenReturn(Collections.emptyList());

    assertThat(this.transactionService.getTransactions().size(), is(0));
    Mockito.verify(this.transactionRepo, Mockito.times(1)).findAll();
  }

  @Test
  public void getTransactions_whenRecords() {
    Mockito.when(this.transactionRepo.findAll()).thenReturn(Arrays.asList(t1, t2, t3));

    assertThat(this.transactionService.getTransactions().size(), is(3));
    Mockito.verify(this.transactionRepo, Mockito.times(1)).findAll();
  }

  // using argument-captor
  @Test
  public void createTransaction_whenOK() {
    ArgumentCaptor<Transaction> argument = ArgumentCaptor.forClass(Transaction.class);
    Mockito.when(this.transactionRepo.save(argument.capture())).thenReturn(t1);

    Transaction t = this.transactionService
        .createTransaction(t1.getFromAccount(), t1.getToAccount(), t1.getAmount());
    assertTrue(StringUtils.equalsIgnoreCase(t.getFromAccount(), t1.getFromAccount()));
    assertTrue(StringUtils.equalsIgnoreCase(t.getToAccount(), t1.getToAccount()));
    assertEquals(t.getAmount(), t1.getAmount(), 0.01);
    Mockito.verify(this.transactionRepo, Mockito.times(1)).save(argument.getValue());
  }

  // using custom argument-matcher
  @Test
  public void createTransaction_2_whenOK() {
    Mockito.when(this.transactionRepo.save(any(Transaction.class))).thenReturn(t1);

    Transaction t = this.transactionService
        .createTransaction(t1.getFromAccount(), t1.getToAccount(), t1.getAmount());
    assertTrue(StringUtils.equalsIgnoreCase(t.getFromAccount(), t1.getFromAccount()));
    assertTrue(StringUtils.equalsIgnoreCase(t.getToAccount(), t1.getToAccount()));
    assertEquals(t.getAmount(), t1.getAmount(), 0.01);
    Mockito.verify(this.transactionRepo, Mockito.times(1))
        .save(argThat(new TransactionMatcher(t1)));
  }
}
