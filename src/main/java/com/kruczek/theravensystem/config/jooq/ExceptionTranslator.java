package com.kruczek.theravensystem.config.jooq;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

class ExceptionTranslator extends DefaultExecuteListener {

    @Override
    public void exception(ExecuteContext context) {
        final SQLDialect dialect = context.configuration().dialect();
        final SQLExceptionTranslator translator = (dialect != null)
                ? new SQLErrorCodeSQLExceptionTranslator(dialect.name())
                : new SQLStateSQLExceptionTranslator();

        context.exception(translator.translate("Access database using Jooq", context.sql(), context.sqlException()));
    }
}
