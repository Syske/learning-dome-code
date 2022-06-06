package io.github.syske.mybatis.datasource.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切换数据源Advice
 * @author syske
 */
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) {

        String dsId = ds.name();

        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            logger.error("Datasource [{}]not exist，use default datasource.. > {}", ds.name(), point.getSignature());
        } else {
            logger.info("Use DataSource : {} > {}", ds.name(), point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(dsId);
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(TargetDataSource ds) {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}