package com.alexandre.Judo_Candoi_Api;

import com.alexandre.Judo_Candoi_Api.infra.config.RenderDatabaseBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JudoCandoiApiApplication {

    public static void main(String[] args) {
        RenderDatabaseBootstrap.apply();
        SpringApplication.run(JudoCandoiApiApplication.class, args);
    }
}
