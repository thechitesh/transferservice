package com.ingenico.ts.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot Transfer Service Application Class
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.ingenico.ts")
@EnableJpaRepositories("com.ingenico.ts.repository")
@EntityScan("com.ingenico.ts.resources")
@EnableTransactionManagement
public class TransferServiceApplication {


    /**
     * Main method to kick starts Spring Boot applition
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(TransferServiceApplication.class,args);
    }


}



