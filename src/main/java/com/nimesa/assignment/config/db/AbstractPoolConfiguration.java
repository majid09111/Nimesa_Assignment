package com.nimesa.assignment.config.db;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;

public abstract class AbstractPoolConfiguration {

    final PoolConfiguration getPoolConfiguration(DbProperty property)
    {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(property.getDriverClassName());
        poolProperties.setUrl(property.getUrl());
        poolProperties.setUsername(property.getUsername());
        poolProperties.setPassword(property.getPassword());
        poolProperties.setInitialSize(property.getInitialSize());
        poolProperties.setMaxActive(property.getMaxActive());
        poolProperties.setMinIdle(property.getMinIdle());

        poolProperties.setTestOnBorrow(property.getTestOnBorrow());
        poolProperties.setTestOnConnect(property.getTestOnConnect());
        poolProperties.setTestOnReturn(property.getTestOnReturn());
        poolProperties.setValidationQuery(property.getValidationQuery());
        return poolProperties;
    }

    final DataSource getDataSource(PoolConfiguration poolConfiguration)
    {
        DataSource tomcatDs = new DataSource(poolConfiguration);
        tomcatDs.setPoolProperties(poolConfiguration);
        return tomcatDs;
    }

    final LocalContainerEntityManagerFactoryBean createEntityManagerFactory(EntityManagerFactoryBuilder entityManagerFactoryBuilder,
                                                                            javax.sql.DataSource dataSource, String[] packages, String persistanceUnit)
    {
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages(packages)
                .persistenceUnit(persistanceUnit)
                .build();
    }

    final JpaTransactionManager createJpaTransactionManager(EntityManagerFactory entityManagerFactory)
    {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}
