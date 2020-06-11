package com.springtest.spring.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mockito.ArgumentMatcher;

import com.springtest.spring.persistence.model.TransactionDTO;

@Data
@NoArgsConstructor(force = true)
public class TransactionMatcher implements ArgumentMatcher<TransactionDTO> {
  private final TransactionDTO trans1;

  public TransactionMatcher(TransactionDTO trans1) {
    this.trans1 = trans1;
  }

  @Override
  public boolean matches(TransactionDTO argument) {
    return StringUtils.equalsIgnoreCase(this.trans1.getFromAccount(), argument.getFromAccount())
           && StringUtils.equalsIgnoreCase(this.trans1.getToAccount(), argument.getToAccount())
           && this.trans1.getAmount() == argument.getAmount();
  }
}
