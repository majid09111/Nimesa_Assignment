package com.nimesa.assignment.config.db;

import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "rawMasterEntityManagerFactory",
        transactionManagerRef = "rawMasterTransactionManager",
        basePackages = {"com.nimesa.assignment.repository"})
public class MasterdbConfig extends AbstractPoolConfiguration {

    @Bean("rawMasterDataSourceProperties")
    @ConfigurationProperties("db.nimesa.datasource")
    @Primary
    public DbProperty dbProperty() {
        return new DbProperty();
    }

    @Bean("rawMasterPoolConfig")
    @Primary
    public PoolConfiguration poolConfiguration(@Qualifier("rawMasterDataSourceProperties") DbProperty masterProperty) {
        return getPoolConfiguration(masterProperty);
    }

    @Bean("rawMasterDataSource")
    @Primary
    public DataSource dataSource(@Qualifier("rawMasterPoolConfig") PoolConfiguration poolConfiguration) {
        return getDataSource(poolConfiguration);
    }

    @Bean({"rawMasterEntityManagerFactory"})
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder entityManagerFactoryBuilder,
                                                                @Qualifier("rawMasterDataSource") DataSource dataSource) {
        return createEntityManagerFactory(entityManagerFactoryBuilder, dataSource, new String[]{"com.nimesa.assignment.data"},
                "rawMaster");
    }


    @Bean({"rawMasterTransactionManager"})
    @Primary
    public JpaTransactionManager jpaTransactionManager(@Qualifier("rawMasterEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return createJpaTransactionManager(entityManagerFactory);
    }

}
