package com.springtest.spring.config;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Generated // exclude from Jacoco test coverage
@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {
  @Override
  public void onEvent(CacheEvent<?, ?> e) {
    log.info("Cache -> Event: {}, Key: {}, Old: {}, New: {}", e.getType(), e.getKey(), e
        .getOldValue(), e.getNewValue());
  }
}
