package com.hello.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hello.persistence.entity.Transaction;
import com.hello.persistence.repo.TransactionRepo;
import com.hello.util.TransactionMatcher;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest
//@AutoConfigureMockMvc
public class TransactionServiceUT {
  private static Transaction t1;
  private static Transaction t2;
  private static Transaction t3;

  @Mock
  private TransactionRepo transactionRepo;

  @InjectMocks
  private TransactionService transactionService;

  @BeforeAll
  static void setup() {
    t1 = new Transaction(100, "P1", "T1", 1000);
    t2 = new Transaction(101, "P2", "T2", 2000);
    t3 = new Transaction(102, "P3", "T3", 3000);
  }

  @BeforeEach
  public void setUp() {
    //    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getTransaction_whenNotFound() {
    long id = t1.getTransactionId();
    when(this.transactionRepo.findById(id)).thenReturn(Optional.empty());

    Exception ex =
        assertThrows(RuntimeException.class, () -> this.transactionService.getTransaction(id));
    String msg = "Transaction not found for id - " + id;
    assertTrue(StringUtils.equalsIgnoreCase(msg, ex.getMessage()));
    verify(this.transactionRepo, times(1)).findById(id);
  }

  @Test
  public void getTransaction_whenFound() {
    long id = t1.getTransactionId();
    when(this.transactionRepo.findById(id)).thenReturn(Optional.of(t1));

    assertThat(this.transactionService.getTransaction(id), is(t1));
    verify(this.transactionRepo, times(1)).findById(id);
  }

  @Test
  public void getTransactions_whenNoRecords() {
    when(this.transactionRepo.findAll()).thenReturn(Collections.emptyList());

    assertThat(this.transactionService.getTransactions().size(), is(0));
    verify(this.transactionRepo, times(1)).findAll();
  }

  @Test
  public void getTransactions_whenRecords() {
    when(this.transactionRepo.findAll()).thenReturn(Arrays.asList(t1, t2, t3));

    assertThat(this.transactionService.getTransactions().size(), is(3));
    verify(this.transactionRepo, times(1)).findAll();
  }

  // using argument-captor
  @Test
  public void createTransaction_whenOK() {
    ArgumentCaptor<Transaction> argument = ArgumentCaptor.forClass(Transaction.class);
    when(this.transactionRepo.save(argument.capture())).thenReturn(t1);

    Transaction t = this.transactionService
        .createTransaction(t1.getFromAccount(), t1.getToAccount(), t1.getAmount());
    assertTrue(StringUtils.equalsIgnoreCase(t.getFromAccount(), t1.getFromAccount()));
    assertTrue(StringUtils.equalsIgnoreCase(t.getToAccount(), t1.getToAccount()));
    assertEquals(t.getAmount(), t1.getAmount(), 0.01);
    verify(this.transactionRepo, times(1)).save(argument.getValue());
  }

  // using custom argument-matcher
  @Test
  public void createTransaction_2_whenOK() {
    when(this.transactionRepo.save(any(Transaction.class))).thenReturn(t1);

    Transaction t = this.transactionService
        .createTransaction(t1.getFromAccount(), t1.getToAccount(), t1.getAmount());
    assertTrue(StringUtils.equalsIgnoreCase(t.getFromAccount(), t1.getFromAccount()));
    assertTrue(StringUtils.equalsIgnoreCase(t.getToAccount(), t1.getToAccount()));
    assertEquals(t.getAmount(), t1.getAmount(), 0.01);
    verify(this.transactionRepo, times(1)).save(argThat(new TransactionMatcher(t1)));
  }

  @Test
  public void createTransaction_whenNotOK_1() {
    String empty = StringUtils.EMPTY;
    try {
      this.transactionService.createTransaction(empty, t1.getToAccount(), t1.getAmount());
      fail();
    } catch (Exception e) {
      assertTrue(StringUtils.equalsIgnoreCase(e.getMessage(), "FromAc cannot be empty."));
    }
    verify(this.transactionRepo, times(0)).save(any(Transaction.class));
  }

  @Test
  public void createTransaction_whenNotOK_2() {
    String empty = StringUtils.EMPTY;
    try {
      this.transactionService.createTransaction(t1.getFromAccount(), empty, t1.getAmount());
      fail();
    } catch (Exception e) {
      assertTrue(StringUtils.equalsIgnoreCase(e.getMessage(), "ToAc cannot be empty."));
    }
    verify(this.transactionRepo, times(0)).save(any(Transaction.class));
  }

  @Test
  public void createTransaction_whenNotOK_3() {
    try {
      this.transactionService.createTransaction(t1.getFromAccount(), t1.getToAccount(), 0);
      fail();
    } catch (Exception e) {
      assertTrue(StringUtils.equalsIgnoreCase(e.getMessage(), "Amount cannot be zero."));
    }
    verify(this.transactionRepo, times(0)).save(any(Transaction.class));
  }
}
