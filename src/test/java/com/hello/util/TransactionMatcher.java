package com.hello.util;

import org.apache.commons.lang3.StringUtils;
import org.mockito.ArgumentMatcher;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.hello.persistence.entity.Transaction;

@Data
@NoArgsConstructor(force = true)
public class TransactionMatcher implements ArgumentMatcher<Transaction> {
  private final Transaction trans1;

  public TransactionMatcher(Transaction trans1) {
    this.trans1 = trans1;
  }

  @Override
  public boolean matches(Transaction argument) {
    return StringUtils.equalsIgnoreCase(this.trans1.getFromAccount(), argument.getFromAccount()) &&
           StringUtils.equalsIgnoreCase(this.trans1.getToAccount(), argument.getToAccount()) &&
           this.trans1.getAmount() == argument.getAmount();
  }
}
