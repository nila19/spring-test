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
import com.hello.persistence.model.TransactionDTO;
import com.hello.service.TransactionKvService;

// incorrect way to do unit test for controller layer
// below implementation is very similar to service layer UT.

@ExtendWith(MockitoExtension.class)
//@WebMvcTest
public class TransactionControllerUT {
  private static TransactionDTO t1;
  private static TransactionDTO t2;
  private static TransactionDTO t3;

  @Mock
  private TransactionKvService transactionKvService;

  @InjectMocks
  private TransactionController transactionController;

  @InjectMocks
  private HelloController helloController;

  @BeforeAll
  static void setup() {
    t1 = new TransactionDTO(100, "P1", "T1", 1000);
    t2 = new TransactionDTO(101, "P2", "T2", 2000);
    t3 = new TransactionDTO(102, "P3", "T3", 3000);
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
    when(this.transactionKvService.getTransaction(id)).thenThrow(new RuntimeException(msg));

    Exception ex =
        assertThrows(RuntimeException.class, () -> this.transactionController.getTransaction(t1));
    assertTrue(StringUtils.equalsIgnoreCase(msg, ex.getMessage()));
    verify(this.transactionKvService, times(1)).getTransaction(id);
  }

  @Test
  public void getTransaction_whenFound() {
    long id = t1.getTransactionId();
    when(this.transactionKvService.getTransaction(id)).thenReturn(t1);

    assertThat(this.transactionController.getTransaction(t1), is(t1));
    verify(this.transactionKvService, times(1)).getTransaction(id);
  }

  @Test
  public void getAll_whenNoRecords() {
    when(this.transactionKvService.getTransactions()).thenReturn(Collections.emptyList());

    assertThat(this.transactionController.getAll().size(), is(0));
    verify(this.transactionKvService, times(1)).getTransactions();
  }

  @Test
  public void getAll_whenRecords() {
    when(this.transactionKvService.getTransactions()).thenReturn(Arrays.asList(t1, t2, t3));

    assertThat(this.transactionController.getAll().size(), is(3));
    verify(this.transactionKvService, times(1)).getTransactions();
  }

  @Test
  public void saveTransaction_whenOK() {
    when(this.transactionKvService.createTransaction(t1)).thenReturn(t1);

    assertThat(this.transactionController.saveTransaction(t1), is(t1));
    verify(this.transactionKvService, times(1)).createTransaction(t1);
  }
}
