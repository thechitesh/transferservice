package com.ingenico.ts.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.ingenico.ts")
@EnableJpaRepositories("com.ingenico.ts.repository")
@EntityScan("com.ingenico.ts.resources")
public class TransferServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(TransferServiceApplication.class,args);
    }


}



