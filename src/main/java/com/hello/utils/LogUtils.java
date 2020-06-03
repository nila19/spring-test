package com.hello.utils;

import static net.logstash.logback.argument.StructuredArguments.kv;
import static net.logstash.logback.marker.Markers.append;

import org.slf4j.Logger;

public class LogUtils {
  // using structured arguments
  public static void logInfo(Logger log, Object data, String msg) {
    log.info(msg, kv("data", data));
  }

  // using markers
  public static void logError(Logger log, Object data, String msg, Exception e) {
    log.error(append("data", data), msg, e);
  }
}
