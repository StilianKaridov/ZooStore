package com.tinqin.zoostore.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.tinqin.zoostore")
@EntityScan(basePackages = "com.tinqin.zoostore.persistence.entity")
@EnableJpaRepositories(basePackages = "com.tinqin.zoostore.persistence.repository")
public class ZooStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZooStoreApplication.class, args);
    }

}
