package com.kruczek.theravensystem.config.jooq;

import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.jooq.tools.JooqLogger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_NESTED;

class JooqTransactionProvider implements TransactionProvider {

    private static final JooqLogger log = JooqLogger.getLogger(JooqTransactionProvider.class);

    private final DataSourceTransactionManager txMgr;

    public JooqTransactionProvider(DataSourceTransactionManager txMgr) {
        this.txMgr = txMgr;
    }

    @Override
    public void begin(TransactionContext ctx) {
        log.info("Begin transaction");
        TransactionStatus tx = txMgr.getTransaction(new DefaultTransactionDefinition(PROPAGATION_NESTED));
        ctx.transaction(new SpringTransaction(tx));
    }

    @Override
    public void commit(TransactionContext ctx) {
        log.info("Commit transaction");
        txMgr.commit(((SpringTransaction) ctx.transaction()).tx);
    }

    @Override
    public void rollback(TransactionContext ctx) {
        log.info("Rollback transaction!!!");
        txMgr.rollback(((SpringTransaction) ctx.transaction()).tx);
    }
}

