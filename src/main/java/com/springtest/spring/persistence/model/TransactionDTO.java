package com.springtest.spring.persistence.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class TransactionDTO implements Serializable {
  private static final long serialVersionUID = 1448221334863874075L;

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
