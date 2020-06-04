package com.hello.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableJpaRepositories
//@EnableJpaAuditing
//@EnableTransactionManagement
@EntityScan(basePackages = {"com.hello.persistence"})
public class JpaConfig {
}
