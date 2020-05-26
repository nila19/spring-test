package com.hello.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.Application;
import com.hello.persistence.entity.Transaction;
import com.hello.service.TransactionService;

@RunWith(SpringJUnit4ClassRunner.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@WebAppConfiguration
//@ContextConfiguration(classes = WebConfig.class)
public class TransactionApiUT {
  private final static String URI = "/trans/getAll";
  private static final Transaction t1 = new Transaction(100, "P1", "T1", 1000);
  private static final Transaction t2 = new Transaction(101, "P2", "T2", 2000);
  private static final Transaction t3 = new Transaction(102, "P3", "T3", 3000);

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TransactionService transactionService;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getAll_whenFound() throws Exception {
    List<Transaction> transactions = Arrays.asList(t1, t2, t3);
    Mockito.when(this.transactionService.getTransactions()).thenReturn(transactions);
    String json = this.objectMapper.writeValueAsString(transactions);

    this.mockMvc.perform(MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(json));

    Mockito.verify(this.transactionService, Mockito.times(1)).getTransactions();
  }
}
