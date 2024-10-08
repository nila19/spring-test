package com.springtest.spring.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.springtest.spring.Application;
import com.springtest.spring.api.model.Request;
import com.springtest.spring.config.WebConfig;
import com.springtest.spring.persistence.model.TransactionDTO;
import com.springtest.spring.util.API;
import com.springtest.spring.util.TransactionMatcher;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class,
    properties = {"command.line.runner.enabled=false"})
@AutoConfigureMockMvc
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class TransactionApiIT {
  private static TransactionDTO t1;
  private static TransactionDTO t2;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @BeforeAll
  static void setup() {
    t1 = new TransactionDTO(100, "Acct 1", "Acct 2", 200.50);
    t2 = new TransactionDTO(200, "P2", "T2", 3000);
  }

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getAll_whenFound() throws Exception {
    MockHttpServletRequestBuilder request = post(API.GET_ALL.url)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = this.mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isOk()).andReturn();

    String json = mvcResult.getResponse().getContentAsString();
    List<TransactionDTO> trans =
        this.objectMapper.readValue(json, new TypeReference<List<TransactionDTO>>() {});

    // equal to the # of records inserted via data.sql
    assertEquals(trans.size(), 4);
  }

  @Test
  public void getTransaction_whenNotFound() throws Exception {
    Request req = new Request(200);
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
    Request req = new Request(100);
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
