package com.springtest.spring.jmx;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.springtest.spring.persistence.repo.TransactionRepo;

@Component
public class DBHealthIndicator implements HealthIndicator {
  private final TransactionRepo transactionRepo;

  public DBHealthIndicator(TransactionRepo transactionRepo) {
    this.transactionRepo = transactionRepo;
  }

  @Override
  public Health health() {
    long count = this.transactionRepo.count(); // perform some specific health check
    if (count <= 0) {
      return Health.down().withDetail("Error Code", count).build();
    }
    return Health.up().build();
  }
}
