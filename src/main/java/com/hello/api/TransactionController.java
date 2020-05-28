package com.hello.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.hello.persistence.entity.Transaction;
import com.hello.service.TransactionService;

@RestController
@RequestMapping(value = "/trans")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping(value = "/save", consumes = APPLICATION_JSON_VALUE, produces =
      APPLICATION_JSON_VALUE)
  public Transaction saveTransaction(@RequestBody Transaction t) {
    return this.transactionService
        .createTransaction(t.getFromAccount(), t.getToAccount(), t.getAmount());
  }

  @PostMapping(value = "/get", consumes = APPLICATION_JSON_VALUE, produces =
      APPLICATION_JSON_VALUE)
  public Transaction getTransaction(@RequestBody Transaction t) {
    return this.transactionService.getTransaction(t.getTransactionId());
  }

  @PostMapping(value = "/getAll")
  public List<Transaction> getAll() {
    return this.transactionService.getTransactions();
  }
}
