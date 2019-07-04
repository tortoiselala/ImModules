package com.tortoiselala.dao.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tortoiselala
 */
public class DataSources extends AbstractRoutingDataSource {

    /**
     * 主数据源
     */
    private DataSource master;
    /**
     * 从数据源
     */
    private DataSource slave;

    /**
     * 数据源设置完成后进行必要的校验与设置
     */
    @Override
    public void afterPropertiesSet() {
        if (this.master == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }

        if (this.slave == null) {
            throw new IllegalArgumentException("Property 'readDataSource' is required");
        }

        setDefaultTargetDataSource(master);

        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceType.MASTER.name(), master);
        targetDataSources.put(DataSourceType.SLAVE.name(), slave);

        // 设置数据源
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceType source = DataSourceContextHolder.get();

        if(source == null || source == DataSourceType.MASTER){
            return DataSourceType.MASTER.name();
        }
        return DataSourceType.SLAVE.name();
    }

    public void setMaster(DataSource master){
        this.master = master;
    }

    public DataSource getMaster(){
        return master;
    }

    public void setSlave(DataSource slave){
        this.slave = slave;
    }

    public DataSource getSlave() {
        return slave;
    }
}
