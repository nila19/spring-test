package com.hello;

import java.util.Map;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.hello.service.TransactionKvService;

@Generated // exclude from Jacoco test coverage
@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Slf4j
@Component
public class AppRunner implements CommandLineRunner {
  private final TransactionKvService transactionKvService;
  private final HazelcastInstance hazelcastInstance;

  public AppRunner(TransactionKvService transactionKvService,
      @Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
    this.transactionKvService = transactionKvService;
    this.hazelcastInstance = hazelcastInstance;
  }

  @Override
  public void run(String... args) {
    log.info("===> Running AppRunner...");
    //    this.checkCaching();
    //    this.checkHazelCast();
    log.info("===> Ending AppRunner...");
  }

  private void checkCaching() {
    log.info("===> All - " + this.transactionKvService.getTransactions().size());
    log.info("===> All - " + this.transactionKvService.getTransactions().size());
    log.info("===> All - " + this.transactionKvService.getTransactions().size());
    log.info("===> All - " + this.transactionKvService.getTransactions().size());
    log.info("===> 100 - " + this.transactionKvService.getTransaction(100));
    log.info("===> 100 - " + this.transactionKvService.getTransaction(100));
    log.info("===> 101 - " + this.transactionKvService.getTransaction(101));
    log.info("===> 101 - " + this.transactionKvService.getTransaction(101));
    log.info("===> 100 - " + this.transactionKvService.getTransaction(100));
    log.info("===> All - " + this.transactionKvService.getTransactions().size());
  }

  private void checkHazelCast() {
    Map<Long, String> map = this.hazelcastInstance.getMap("purchaseData");
    FlakeIdGenerator idGenerator = this.hazelcastInstance.getFlakeIdGenerator("newId");
    for (int i = 0; i < 10; i++) {
      map.put(idGenerator.newId(), "message-" + i);
    }
    log.info("====> Added 10 items to Hazelcast map...");
  }
}
