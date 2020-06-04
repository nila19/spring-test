package com.hello.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.hello.api.model.Request;
import com.hello.exception.HelloException;
import com.hello.persistence.model.TransactionDTO;
import com.hello.service.TransactionKvService;
import com.hello.util.API;
import com.hello.util.TransactionMatcher;

// right way to do unit test for controller layer

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class TransactionApiUT {
  private static TransactionDTO t1;
  private static TransactionDTO t2;
  private static TransactionDTO t3;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TransactionKvService transactionKvService;

  @Autowired
  private MockMvc mockMvc;

  @BeforeAll
  static void setup() {
    t1 = new TransactionDTO(100, "P1", "T1", 1000);
    t2 = new TransactionDTO(101, "P2", "T2", 2000);
    t3 = new TransactionDTO(102, "P3", "T3", 3000);
  }

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getAll_whenFound() throws Exception {
    List<TransactionDTO> transactions = Arrays.asList(t1, t2, t3);
    String json = this.objectMapper.writeValueAsString(transactions);
    when(this.transactionKvService.getTransactions()).thenReturn(transactions);

    MockHttpServletRequestBuilder request = post(API.GET_ALL.url)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);
    this.mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(json));

    verify(this.transactionKvService, times(1)).getTransactions();
  }

  @Test
  public void getTransaction_whenNotFound() throws Exception {
    long transId = 200;
    String msg = "Transaction not found for id - " + transId;
    when(this.transactionKvService.getTransaction(transId))
        .thenThrow(new HelloException(msg));

    Request req = new Request(transId);
    MockHttpServletRequestBuilder request = post(API.GET.url)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(req));
    this.mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void getTransaction_whenFound() throws Exception {
    long transId = 100;
    when(this.transactionKvService.getTransaction(transId)).thenReturn(t1);

    Request req = new Request(transId);
    MockHttpServletRequestBuilder request = post(API.GET.url)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(req));
    MvcResult mvcResult = this.mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isOk()).andReturn();

    String json = mvcResult.getResponse().getContentAsString();
    TransactionDTO tran = this.objectMapper.readValue(json, new TypeReference<TransactionDTO>() {});

    TransactionMatcher matcher = new TransactionMatcher(t1);
    assertTrue(matcher.matches(tran));
  }

  @Test
  public void saveTransaction_whenOK() throws Exception {
    when(this.transactionKvService.createTransaction(t2)).thenReturn(t2);

    MockHttpServletRequestBuilder request = post(API.SAVE.url)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(t2));
    MvcResult mvcResult = this.mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isOk()).andReturn();

    String json = mvcResult.getResponse().getContentAsString();
    TransactionDTO tran = this.objectMapper.readValue(json, new TypeReference<TransactionDTO>() {});

    TransactionMatcher matcher = new TransactionMatcher(t2);
    assertTrue(matcher.matches(tran));
  }
}
