package com.springtest.spring;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Generated
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}

// enable building war
//public class Application extends SpringBootServletInitializer {
//
//  public static void main(String[] args) {
//    SpringApplication.run(Application.class, args);
//  }
//
//  @Override
//  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//    return application.sources(Application.class);
//  }
//}
