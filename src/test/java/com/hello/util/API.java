package com.hello.util;

public enum API {
  GET_ALL("/trans/getAll"), GET("/trans/get"), SAVE("/trans/save");

  public final String url;

  API(String url) {
    this.url = url;
  }
}
