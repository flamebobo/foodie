package com.example.tccdemo.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.io.IOException;

@Configuration
@MapperScan(value = "com.example.tccdemo.db131.dao",sqlSessionFactoryRef = "factoryBean131")
public class ConfigDb131 {

    /**
     * 配置数据源
     * @return
     */
    @Bean("db131")
    public DataSource db131() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("12345678");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/xa_131");

        return dataSource;
    }

    /**
     * 配置mybatis数据源
     * @param dataSource
     * @return
     * @throws IOException
     */
    @Bean("factoryBean131")
    public SqlSessionFactoryBean factoryBean(@Qualifier("db131") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        factoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

        factoryBean.setMapperLocations(resourceResolver.getResources("mybatis/db131/*.xml"));
        return factoryBean;
    }

    /**
     * 配置事务管理器 PlatformTransactionManager
     * @param dataSource
     * @return
     */
    @Bean("tm131")
    public PlatformTransactionManager transactionManager(@Qualifier("db131") DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }
}
