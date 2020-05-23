package com.hello.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TRANSACTIONS")
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
}
