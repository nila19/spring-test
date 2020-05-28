package com.hello.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hello.api.model.Response;
import com.hello.persistence.entity.Transaction;
import com.hello.service.TransactionService;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest
public class TransactionControllerUT {
  private static Transaction t1;
  private static Transaction t2;
  private static Transaction t3;

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  private TransactionController transactionController;

  @InjectMocks
  private HelloController helloController;

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
  public void hello_whenOK() {
    Response response = new Response(0, "Hello World!!!");
    assertThat(this.helloController.hello(), is(response));
  }

  @Test
  public void getTransaction_whenNotFound() {
    long id = t1.getTransactionId();
    String msg = "Transaction not found for id - " + id;
    when(this.transactionService.getTransaction(id)).thenThrow(new RuntimeException(msg));

    Exception ex =
        assertThrows(RuntimeException.class, () -> this.transactionController.getTransaction(t1));
    assertTrue(StringUtils.equalsIgnoreCase(msg, ex.getMessage()));
    verify(this.transactionService, times(1)).getTransaction(id);
  }

  @Test
  public void getTransaction_whenFound() {
    long id = t1.getTransactionId();
    when(this.transactionService.getTransaction(id)).thenReturn(t1);

    assertThat(this.transactionController.getTransaction(t1), is(t1));
    verify(this.transactionService, times(1)).getTransaction(id);
  }

  @Test
  public void getAll_whenNoRecords() {
    when(this.transactionService.getTransactions()).thenReturn(Collections.emptyList());

    assertThat(this.transactionController.getAll().size(), is(0));
    verify(this.transactionService, times(1)).getTransactions();
  }

  @Test
  public void getAll_whenRecords() {
    when(this.transactionService.getTransactions()).thenReturn(Arrays.asList(t1, t2, t3));

    assertThat(this.transactionController.getAll().size(), is(3));
    verify(this.transactionService, times(1)).getTransactions();
  }

  @Test
  public void saveTransaction_whenOK() {
    when(this.transactionService
        .createTransaction(t1.getFromAccount(), t1.getToAccount(), t1.getAmount()))
        .thenReturn(t1);

    assertThat(this.transactionController.saveTransaction(t1), is(t1));
    verify(this.transactionService, times(1))
        .createTransaction(t1.getFromAccount(), t1.getToAccount(), t1.getAmount());
  }
}
