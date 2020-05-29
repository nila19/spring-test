package com.hello.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.persistence.model.TransactionDTO;
import com.hello.service.TransactionService;

@RestController
@RequestMapping(value = "/trans")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping(value = "/save", consumes = APPLICATION_JSON_VALUE, produces =
      APPLICATION_JSON_VALUE)
  public TransactionDTO saveTransaction(@RequestBody TransactionDTO dto) {
    return this.transactionService.createTransaction(dto);
  }

  @PostMapping(value = "/get", consumes = APPLICATION_JSON_VALUE, produces =
      APPLICATION_JSON_VALUE)
  public TransactionDTO getTransaction(@RequestBody TransactionDTO dto) {
    return this.transactionService.getTransaction(dto.getTransactionId());
  }

  @PostMapping(value = "/getAll", produces = APPLICATION_JSON_VALUE)
  public List<TransactionDTO> getAll() {
    return this.transactionService.getTransactions();
  }
}
