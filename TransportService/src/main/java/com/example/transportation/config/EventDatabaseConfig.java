package com.example.transportation.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "eventsEntityManagerFactory",
        transactionManagerRef = "eventsTransactionManager",
        basePackages = { "com.example.transportation.event.repo" }
)
public class EventDatabaseConfig {

    @Bean(name = "eventsDataSource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "eventsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    eventsEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("eventsDataSource") DataSource dataSource
    ) {
        return
                builder
                        .dataSource(dataSource)
                        .packages("com.example.transportation.event.domain")
                        .persistenceUnit("events")
                        .build();
    }
    @Bean(name = "eventsTransactionManager")
    public PlatformTransactionManager eventsTransactionManager(
            @Qualifier("eventsEntityManagerFactory") EntityManagerFactory
                    eventsEntityManagerFactory
    ) {
        return new JpaTransactionManager(eventsEntityManagerFactory);
    }
}
