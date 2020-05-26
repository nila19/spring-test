package com.hello.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TRANSACTIONS")
@NoArgsConstructor(force = true)
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TRANSACTION_ID")
  private long transactionId;

  @Column(name = "FROM_ACCT")
  private String fromAccount;

  @Column(name = "TO_ACCT")
  private String toAccount;

  @Column(name = "AMOUNT")
  private double amount;

  public Transaction(long transactionId, String fromAccount, String toAccount, double amount) {
    this(fromAccount, toAccount, amount);
    this.transactionId = transactionId;
  }

  public Transaction(String fromAccount, String toAccount, double amount) {
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
  }

  public Transaction(Transaction t2, long transactionId) {
    this.fromAccount = t2.fromAccount;
    this.toAccount = t2.toAccount;
    this.amount = t2.amount;
    this.transactionId = transactionId;
  }
}
