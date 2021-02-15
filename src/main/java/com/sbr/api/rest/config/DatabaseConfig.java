package com.sbr.api.rest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.sbr.api.rest.repository")
@EntityScan(basePackages = "com.sbr.api.rest.domain")
public class DatabaseConfig {
}
