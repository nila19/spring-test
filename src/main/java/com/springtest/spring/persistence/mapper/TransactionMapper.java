package com.springtest.spring.persistence.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.springtest.spring.persistence.entity.Transaction;
import com.springtest.spring.persistence.model.TransactionDTO;
import com.springtest.spring.utils.Utils;

@Component
public class TransactionMapper {
  private final ModelMapper mapper;

  public TransactionMapper() {
    this.mapper = this.buildMapper();
  }

  private ModelMapper buildMapper() {
    ModelMapper mapper = Utils.buildMapper();

    PropertyMap<Transaction, TransactionDTO> toDtoMap =
        new PropertyMap<Transaction, TransactionDTO>() {
          @Override
          protected void configure() {
            this.map().setAmount(this.source.getAmount());
          }
        };
    PropertyMap<TransactionDTO, Transaction> toEntityMap =
        new PropertyMap<TransactionDTO, Transaction>() {
          @Override
          protected void configure() {
            this.skip().setTransactionId(0);
          }
        };

    mapper.addMappings(toDtoMap);
    mapper.addMappings(toEntityMap);
    return mapper;
  }

  public TransactionDTO toDto(Transaction entity) {
    return this.mapper.map(entity, TransactionDTO.class);
  }

  public Transaction toEntity(TransactionDTO dto) {
    return this.mapper.map(dto, Transaction.class);
  }
}
