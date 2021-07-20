package com.dm.ds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
@EntityScan
@EnableJpaRepositories
public class DataServiceApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DataServiceApplication.class, args);
    }
}
