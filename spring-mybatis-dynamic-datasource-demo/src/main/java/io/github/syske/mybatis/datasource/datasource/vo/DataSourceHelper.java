package io.github.syske.mybatis.datasource.datasource.vo;

import io.github.syske.mybatis.datasource.datasource.DynamicDataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceHelper {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceHelper.class);

    public static final String SYSKE_DATABASE_PREFIX = "syske_";

    public static final String MESSAGE_DATABASE_NAME = "ds1";


    /**
     * 切换到指定的数据库
     * @param datasource
     */
    public static void changeToSpecificDataSource(String datasource) {
        DynamicDataSourceContextHolder.clearDataSourceType();
        String dbName = SYSKE_DATABASE_PREFIX + datasource;

        //logger.info("--切换查询数据库到--:" + dbName);
        DynamicDataSourceContextHolder.setDataSourceType(dbName);
    }

    public static void changeToMessageDb() {
        DynamicDataSourceContextHolder.clearDataSourceType();
        DynamicDataSourceContextHolder.setDataSourceType(MESSAGE_DATABASE_NAME);
    }

    /**
     * 重置链接到主库
     */
    public static void reset() {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
