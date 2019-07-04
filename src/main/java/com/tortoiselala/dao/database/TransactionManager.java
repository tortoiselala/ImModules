package com.tortoiselala.dao.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * @author tortoiselala
 */
public class TransactionManager extends DataSourceTransactionManager {


    private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        logger.debug("running TransactionManager#doBegin()");
        //设置数据源
        boolean readOnly = definition.isReadOnly();
        if(readOnly) {
            DataSourceContextHolder.put(DataSourceType.SLAVE);
        } else {
            DataSourceContextHolder.put(DataSourceType.MASTER);
        }
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        DataSourceContextHolder.clear();
    }
}
