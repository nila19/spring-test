package com.springtest.spring.persistence.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TRANSACTIONS")
@NoArgsConstructor(force = true)
@AllArgsConstructor
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

  @CreatedDate
  @Column(name = "CREATED_DATE")
  private Date createdDate;

  //  @CreatedBy
  //  private User user;

  public Transaction(long transactionId, String fromAccount, String toAccount, double amount) {
    this(fromAccount, toAccount, amount);
    this.transactionId = transactionId;
  }

  public Transaction(String fromAccount, String toAccount, double amount) {
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
  }
}
