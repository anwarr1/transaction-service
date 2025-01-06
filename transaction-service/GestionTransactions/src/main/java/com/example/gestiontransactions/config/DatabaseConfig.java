package com.example.gestiontransactions.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/batch_db")
                .username("root")
                .password("moha2022")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}