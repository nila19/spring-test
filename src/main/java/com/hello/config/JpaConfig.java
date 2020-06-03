package com.hello.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableJpaRepositories
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan(basePackages = {"com.hello.persistence"})
public class JpaConfig {
}
