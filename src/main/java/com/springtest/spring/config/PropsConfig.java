package com.springtest.spring.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan("com.springtest.spring.config.props")
public class PropsConfig {
}
