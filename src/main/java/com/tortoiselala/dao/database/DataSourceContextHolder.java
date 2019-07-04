package com.tortoiselala.dao.database;

import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当前线程DataSource持有者
 * @author tortoiselala
 */
public class DataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<DataSourceType> holder = new ThreadLocal<>();

    private DataSourceContextHolder() {
    }

    public static void put(DataSourceType source){
        Assert.notNull(source, "Database cannot be null");
        holder.set(source);
    }

    public static DataSourceType get(){
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
