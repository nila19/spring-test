package com.hello.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {
  @Bean
  public Config hazelCastConfig() {
    Config config = new Config();
    config.setInstanceName("hazelcast-instance")
        .addMapConfig(
            new MapConfig()
                .setName("configuration")
                .setBackupCount(1)
                .setTimeToLiveSeconds(30)
                .setStatisticsEnabled(true)
                //                .setMaxSizeConfig(new MaxSizeConfig(200,
                //                    MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                //                .setEvictionPolicy(EvictionPolicy.LRU)
                .setTimeToLiveSeconds(-1));
    return config;
  }
}
