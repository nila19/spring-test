package com.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.hello.service.TransactionService;

@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Slf4j
@Component
public class AppRunner implements CommandLineRunner {
  private final TransactionService transactionService;

  public AppRunner(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Override
  public void run(String... args) {
    log.info("===> Running AppRunner...");
    log.info("===> All - " + this.transactionService.getTransactions().size());
    log.info("===> All - " + this.transactionService.getTransactions().size());
    log.info("===> All - " + this.transactionService.getTransactions().size());
    log.info("===> All - " + this.transactionService.getTransactions().size());
    log.info("===> 100 - " + this.transactionService.getTransaction(100));
    log.info("===> 100 - " + this.transactionService.getTransaction(100));
    log.info("===> 101 - " + this.transactionService.getTransaction(101));
    log.info("===> 101 - " + this.transactionService.getTransaction(101));
    log.info("===> 100 - " + this.transactionService.getTransaction(100));
    log.info("===> All - " + this.transactionService.getTransactions().size());
    log.info("===> Ending AppRunner...");
  }
}
