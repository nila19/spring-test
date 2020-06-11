package com.springtest.spring.api;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springtest.spring.api.model.Response;
import com.springtest.spring.config.props.HelloProps;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

  private final HelloProps helloProps;

  public HelloController(HelloProps helloProps) {
    this.helloProps = helloProps;
  }

  @Hidden
  @GetMapping(value = "/hello")
  public Response hello() {
    String msg = this.helloProps.getMessage() + " : " + this.helloProps.getFromId();
    return new Response(0, msg);
  }
}
