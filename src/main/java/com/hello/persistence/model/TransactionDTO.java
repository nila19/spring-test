package com.hello.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class TransactionDTO {
  private long transactionId;
  private String fromAccount;
  private String toAccount;
  private double amount;

  public TransactionDTO(String fromAccount, String toAccount, double amount) {
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
  }
}
