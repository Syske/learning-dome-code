package io.github.syske.mybatis.datasource.datasource;

import io.github.syske.mybatis.datasource.datasource.service.DynamicDataSourceService;
import io.github.syske.mybatis.datasource.datasource.vo.CorpDataSourceNode;
import io.github.syske.mybatis.datasource.datasource.vo.DataSourceAuthInfo;
import io.github.syske.mybatis.datasource.datasource.vo.DataSourceHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author syske
 */
@Primary
@Component
public class DynamicDataSource extends AbstractDataSource implements CommandLineRunner {

    private static final String DEFAULT_DB = "syske";

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    @Autowired
    private DataSourceProperties defaultDataSourceProperties;

    @Autowired
    private DataSourceProperties polarDataSourceProperties;

    @Autowired
    private DataSource polarDataSource;

    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    @Autowired
    private DataSource defaultDataSource;



    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = this.determineTargetDataSource().getConnection();
        connection.setCatalog(getCurrentDbName());
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = this.determineTargetDataSource().getConnection(username, password);
        connection.setCatalog(getCurrentDbName());
        return connection;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return iface.isInstance(this) ? (T) this : this.determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this) || this.determineTargetDataSource().isWrapperFor(iface);
    }


    protected DataSource determineTargetDataSource() {

        DataSource dataSource = null;
        Map<String, DataSource> resolvedDataSources = dynamicDataSourceService.getResolvedDataSources();
        if (resolvedDataSources != null) {
            String lookupKey = getDbServerByDbName();
            if (StringUtils.isNotBlank(lookupKey)) {
                dataSource = resolvedDataSources.get(lookupKey);
            }
        }
        if (dataSource == null) {
            dataSource = defaultDataSource;
        }
        return dataSource;
    }

    /**
     * 通过dbName获取dbServer
     *
     * @return
     */
    protected String getDbServerByDbName() {
        String dbName = getCurrentDbName();
        return dynamicDataSourceService.getDbServerByDbName(dbName);
    }

    /**
     * 查询当前线程上下文对应的库名
     *
     * @return
     */
    protected String getCurrentDbName() {
        String dbName = DynamicDataSourceContextHolder.getDataSourceType();
        if (StringUtils.isBlank(dbName)) {
            dbName = DEFAULT_DB;
        }
        return dbName;
    }


    @Override
    public void run(String... args) {

        CorpDataSourceNode node = new CorpDataSourceNode();
        node.setNode("message");
        DataSourceAuthInfo dataSourceAuthInfo = new DataSourceAuthInfo();
        dataSourceAuthInfo.setJdbcUrl(polarDataSourceProperties.getUrl());
        dataSourceAuthInfo.setPassword(polarDataSourceProperties.getPassword());
        dataSourceAuthInfo.setUsername(polarDataSourceProperties.getUsername());
        node.setConfig(dataSourceAuthInfo);

        dynamicDataSourceService.createDataSource(node);

        CorpDataSourceNode coolNode = new CorpDataSourceNode();
        coolNode.setNode("syske.mysql.rds");
        DataSourceAuthInfo coolDataSourceAuthInfo = new DataSourceAuthInfo();
        coolDataSourceAuthInfo.setJdbcUrl(defaultDataSourceProperties.getUrl());
        coolDataSourceAuthInfo.setPassword(defaultDataSourceProperties.getPassword());
        coolDataSourceAuthInfo.setUsername(defaultDataSourceProperties.getUsername());
        coolNode.setConfig(coolDataSourceAuthInfo);
        dynamicDataSourceService.createDataSource(coolNode);
        // polarDB
        dynamicDataSourceService.getResolvedDataSources().put(DataSourceHelper.MESSAGE_DATABASE_NAME, polarDataSource);
    }
}
