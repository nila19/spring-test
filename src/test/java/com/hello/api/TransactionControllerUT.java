package com.hello.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hello.persistence.entity.Transaction;
import com.hello.service.TransactionService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class TransactionControllerUT {
  private static final Transaction t1 = new Transaction(100, "P1", "T1", 1000);
  private static final Transaction t2 = new Transaction(101, "P2", "T2", 2000);
  private static final Transaction t3 = new Transaction(102, "P3", "T3", 3000);

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  private TransactionController transactionController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getTransaction_whenNotFound() {
    long id = t1.getTransactionId();
    String msg = "Transaction not found for id - " + id;
    Mockito.when(this.transactionService.getTransaction(id)).thenThrow(new RuntimeException(msg));

    Exception ex =
        assertThrows(RuntimeException.class, () -> this.transactionController.getTransaction(t1));
    assertTrue(StringUtils.equalsIgnoreCase(msg, ex.getMessage()));
    Mockito.verify(this.transactionService, Mockito.times(1)).getTransaction(id);
  }

  @Test
  public void getTransaction_whenFound() {
    long id = t1.getTransactionId();
    Mockito.when(this.transactionService.getTransaction(id)).thenReturn(t1);

    assertThat(this.transactionController.getTransaction(t1), is(t1));
    Mockito.verify(this.transactionService, Mockito.times(1)).getTransaction(id);
  }

  @Test
  public void getAll_whenNoRecords() {
    Mockito.when(this.transactionService.getTransactions()).thenReturn(Collections.emptyList());

    assertThat(this.transactionController.getAll().size(), is(0));
    Mockito.verify(this.transactionService, Mockito.times(1)).getTransactions();
  }

  @Test
  public void getAll_whenRecords() {
    Mockito.when(this.transactionService.getTransactions()).thenReturn(Arrays.asList(t1, t2, t3));

    assertThat(this.transactionController.getAll().size(), is(3));
    Mockito.verify(this.transactionService, Mockito.times(1)).getTransactions();
  }

  @Test
  public void saveTransaction_whenOK() {
    Mockito.when(this.transactionService
        .createTransaction(t1.getFromAccount(), t1.getToAccount(), t1.getAmount()))
        .thenReturn(t1);

    assertThat(this.transactionController.saveTransaction(t1), is(t1));
    Mockito.verify(this.transactionService, Mockito.times(1))
        .createTransaction(t1.getFromAccount(), t1.getToAccount(), t1.getAmount());
  }
}
