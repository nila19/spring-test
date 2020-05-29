package com.hello.utils;

import org.modelmapper.ModelMapper;

public class Utils {
  public Utils() throws Exception {
    throw new Exception("Singleton class; cannot be instantiated");
  }

  public static ModelMapper buildMapper() {
    return new ModelMapper();
  }
}
