package com.kruczek.theravensystem.config.jooq;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
class JooqConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSourceTransactionManager txMgr;

    @Bean
    DSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    ExceptionTranslator exceptionTransformer() {
        return new ExceptionTranslator();
    }

    @Bean
    JooqTransactionProvider jooqTransactionProvider() {
        return new JooqTransactionProvider(txMgr);
    }

    @Bean
    DefaultConfiguration configuration() {
        final DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(jooqTransactionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));
        return jooqConfiguration;
    }
}
