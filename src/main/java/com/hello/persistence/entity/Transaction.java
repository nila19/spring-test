package com.hello.persistence.entity;

import java.io.Serializable;
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
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@KeySpace
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TRANSACTIONS")
public class Transaction implements Serializable {
  private static final long serialVersionUID = 596484020740973272L;

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
