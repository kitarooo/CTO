package com.example.cto;

import com.example.cto.config.KafkaTopicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class CtoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CtoApplication.class, args);
    }

}
