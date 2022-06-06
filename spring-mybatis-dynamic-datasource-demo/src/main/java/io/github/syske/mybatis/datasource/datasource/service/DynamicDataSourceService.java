package io.github.syske.mybatis.datasource.datasource.service;

import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.syske.mybatis.datasource.datasource.vo.CorpDataSourceNode;
import io.github.syske.mybatis.datasource.datasource.vo.DataSourceAuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;


/**
 *  动态数据源示例
 * @author syske
 */
@Service
public class DynamicDataSourceService {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceService.class);

    @Autowired
    private DataSourceProperties polarDataSourceProperties;

    /**
     * 数据库实例和数据源的映射
     */
    private Map<String, DataSource> resolvedDataSources = Maps.newHashMap();

    private static Map<String, String> dbServerDbNam =  Maps.newHashMap();

    static {
        dbServerDbNam.put("message", "ds1");
        dbServerDbNam.put("syske", "syske.mysql.rds");
    }

    /**
     * 根据dbName查询dbServer
     *
     * @param dbName
     * @return
     */
    public String getDbServerByDbName(String dbName) {
        return dbServerDbNam.get(dbName);
    }

    /**
     * 根据配置创建dataSource, 如果该节点设置了自己的最大连接数, 则覆盖默认的最大连接数
     *
     * @param node
     * @return
     */
    private DataSource buildDataSourceByConfig(CorpDataSourceNode node) {

        if (node == null) {
            return null;
        }
        DataSourceAuthInfo config = node.getConfig();
        String url = config.getJdbcUrl();
        String password = config.getPassword();
        String username = config.getUsername();
        Integer maxPoolSize = config.getMaxPoolSize();

        HikariConfig hikariConfig = new HikariConfig();
        BeanUtils.copyProperties(polarDataSourceProperties, hikariConfig);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        if (maxPoolSize != null) {
            hikariConfig.setMaximumPoolSize(maxPoolSize);
        }

        String poolName = "HikariCP_" + node.getNode();
        hikariConfig.setPoolName(poolName);

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        logger.info("{} dataSource is {}, maxPoolSize={}", poolName, hikariDataSource, hikariDataSource.getMaximumPoolSize());

        return hikariDataSource;
    }

    /**
     * 增加数据源
     *
     * @param node
     */
    public void createDataSource(CorpDataSourceNode node) {
        DataSource dataSource = buildDataSourceByConfig(node);
        if (dataSource != null) {
            this.resolvedDataSources.put(node.getNode(), dataSource);
        }
    }

    public Map<String, DataSource> getResolvedDataSources() {
        return resolvedDataSources;
    }

}
