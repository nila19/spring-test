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
import com.hello.service.TransactionService;

@RestController
@RequestMapping(value = "/trans")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @Operation(summary = "Save the transaction")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Saved the transaction",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = TransactionDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid inputs supplied",
          content = @Content)})
  @PostMapping(value = "/save", consumes = APPLICATION_JSON_VALUE, produces =
      APPLICATION_JSON_VALUE)
  public TransactionDTO saveTransaction(@RequestBody TransactionDTO dto) {
    return this.transactionService.createTransaction(dto);
  }

  @Operation(summary = "Get a transaction by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the transaction",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = TransactionDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
  })
  @PostMapping(value = "/get", consumes = APPLICATION_JSON_VALUE, produces =
      APPLICATION_JSON_VALUE)
  public TransactionDTO getTransaction(@RequestBody TransactionDTO dto) {
    return this.transactionService.getTransaction(dto.getTransactionId());
  }

  @Operation(summary = "Get all transactions")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found transactions",
          content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema =
              @Schema(implementation = TransactionDTO.class))
              )}),
  })
  @PostMapping(value = "/getAll", produces = APPLICATION_JSON_VALUE)
  public List<TransactionDTO> getAll() {
    return this.transactionService.getTransactions();
  }
}
