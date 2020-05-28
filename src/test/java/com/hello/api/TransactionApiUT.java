package com.hello.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.Application;
import com.hello.persistence.entity.Transaction;
import com.hello.service.TransactionService;
import com.hello.util.API;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionApiUT {
  private static Transaction t1;
  private static Transaction t2;
  private static Transaction t3;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TransactionService transactionService;

  @Autowired
  private MockMvc mockMvc;

  @BeforeAll
  static void setup() {
    t1 = new Transaction(100, "P1", "T1", 1000);
    t2 = new Transaction(101, "P2", "T2", 2000);
    t3 = new Transaction(102, "P3", "T3", 3000);
  }

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getAll_whenFound() throws Exception {
    List<Transaction> transactions = Arrays.asList(t1, t2, t3);
    String json = this.objectMapper.writeValueAsString(transactions);
    when(this.transactionService.getTransactions()).thenReturn(transactions);

    MockHttpServletRequestBuilder request = post(API.GET_ALL.url)
        .accept(MediaType.APPLICATION_JSON);
    this.mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(json));

    verify(this.transactionService, times(1)).getTransactions();
  }
}
