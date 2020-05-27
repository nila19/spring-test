package com.hello.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.Application;
import com.hello.config.WebConfig;
import com.hello.persistence.entity.Transaction;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
//@AutoConfigureMockMvc
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class TransactionApiIT {
  private final static String URI = "/trans/getAll";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getAll_whenFound() throws Exception {
    //    List<Transaction> transactions = Arrays.asList(t1, t2, t3);
    //    Mockito.when(this.transactionService.getTransactions()).thenReturn(transactions);
    //    String json = this.objectMapper.writeValueAsString(transactions);
    //    System.out.println("====> JSON: " + json);

    MvcResult mvcResult =
        this.mockMvc.perform(MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk()).andReturn();

    String json2 = mvcResult.getResponse().getContentAsString();
    System.out.println("====> JSON2: " + json2);

    List<Transaction> trans2 =
        this.objectMapper.readValue(json2, new TypeReference<List<Transaction>>() {
        });

    assertEquals(trans2.size(), 4);
    //    Mockito.verify(this.transactionService, Mockito.times(1)).getTransactions();
  }
}
