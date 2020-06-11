package com.springtest.spring.api;

import java.util.Map;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hazel")
public class HazelcastController {
  private final HazelcastInstance hazelcastInstance;

  public HazelcastController(
      @Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
    this.hazelcastInstance = hazelcastInstance;
  }

  @PostMapping(value = "/add")
  public Long add() {
    Map<Long, String> map = this.hazelcastInstance.getMap("purchaseData");
    FlakeIdGenerator idGenerator = this.hazelcastInstance.getFlakeIdGenerator("newId");
    long id = idGenerator.newId();
    map.put(id, "message-" + Math.random());
    return id;
  }

  @GetMapping(value = "/getAll")
  public Map<Long, String> getAll() {
    return this.hazelcastInstance.getMap("purchaseData");
  }
}
