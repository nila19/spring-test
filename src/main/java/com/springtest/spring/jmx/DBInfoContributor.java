package com.springtest.spring.jmx;

import java.util.Collections;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.springtest.spring.persistence.repo.TransactionRepo;

@Component
public class DBInfoContributor implements InfoContributor {
  private final TransactionRepo transactionRepo;

  public DBInfoContributor(TransactionRepo transactionRepo) {
    this.transactionRepo = transactionRepo;
  }

  @Override
  public void contribute(Info.Builder builder) {
    builder.withDetail("db",
        Collections.singletonMap("records", this.transactionRepo.count()));
  }
}
