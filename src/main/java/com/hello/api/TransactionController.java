package com.hello.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.persistence.model.TransactionDTO;
import com.hello.service.TransactionKvService;

@RestController
@RequestMapping(value = "/trans")
@RequiredArgsConstructor
public class TransactionController {
  private static final String JSON = APPLICATION_JSON_VALUE;
  private final TransactionKvService transactionKvService;

  @Operation(summary = "Save the transaction")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Saved the transaction",
          content = {@Content(mediaType = JSON,
              schema = @Schema(implementation = TransactionDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid inputs", content = @Content)})
  @PostMapping(value = "/save", consumes = JSON, produces = JSON)
  public TransactionDTO saveTransaction(@RequestBody TransactionDTO dto) {
    return this.transactionKvService.createTransaction(dto);
  }

  @Operation(summary = "Get a transaction by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the transaction",
          content = {@Content(mediaType = JSON,
              schema = @Schema(implementation = TransactionDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content),
  })
  @PostMapping(value = "/get", consumes = JSON, produces = JSON)
  public TransactionDTO getTransaction(@RequestBody TransactionDTO dto) {
    return this.transactionKvService.getTransaction(dto.getTransactionId());
  }

  @Operation(summary = "Get all transactions")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found transactions",
          content = {@Content(mediaType = JSON, array = @ArraySchema(schema =
          @Schema(implementation = TransactionDTO.class))
          )}),
  })
  @PostMapping(value = "/getAll", produces = JSON)
  public List<TransactionDTO> getAll() {
    return this.transactionKvService.getTransactions();
  }
}
