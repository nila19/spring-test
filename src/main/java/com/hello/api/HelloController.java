package com.hello.api;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.api.model.Response;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

  @Hidden
  @GetMapping(value = "/hello")
  public Response hello() {
    return new Response(0, "Hello World!!!");
  }
}
