package com.hello.persistence.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hello.persistence.entity.Transaction;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TransactionRepoUT {
  private static final Transaction t1 = new Transaction("P1", "T1", 1000);
  private static final Transaction t1_2 = new Transaction("P1", "T2", 1000);
  private static final Transaction t2 = new Transaction("P2", "T2", 2000);
  private static final Transaction t3 = new Transaction("P3", "T3", 3000);

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private TransactionRepo transactionRepo;

  @BeforeAll
  public static void setup() {
    System.out.println("Running before all...");
  }

  @Test
  public void whenFindByFromAccount_thenReturnTransactions() {
    // given
    this.entityManager.persist(t1);
    this.entityManager.persist(t1_2);
    this.entityManager.flush();

    // when
    String fromAccount = t1.getFromAccount();
    List<Transaction> trans = this.transactionRepo.findByFromAccount(fromAccount);
    // then
    trans.forEach(e -> assertThat(e.getFromAccount()).isEqualTo(fromAccount));
  }

  @Test
  public void whenFindAll_thenReturnTransactions() {
    // given
    this.entityManager.persist(t2);
    this.entityManager.persist(t3);
    this.entityManager.flush();

    // when
    List<Transaction> trans = this.transactionRepo.findAll();
    // then
    trans.forEach(e -> assertThat(e.getTransactionId()).isGreaterThan(0));
  }
}
