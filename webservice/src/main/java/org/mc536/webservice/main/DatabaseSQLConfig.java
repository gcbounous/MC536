package org.mc536.webservice.main;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableTransactionManagement
public class DatabaseSQLConfig {

    @Value("${database-sql.driver-class-name}")
    private String driverClassName;

    @Value("${database-sql.jdbc-url}")
    private String jdbcUrl;

    @Value("${database-sql.username}")
    private String user;

    @Value("${database-sql.password}")
    private String password;

    @Value("${database-sql.acquire-increment}")
    private Integer acquireIncrement;

    @Value("${database-sql.idle-connection-test-period}")
    private Integer idleConnectionTestPeriod;

    @Value("${database-sql.max-pool-size}")
    private Integer maxPoolSize;

    @Value("${database-sql.min-pool-size}")
    private Integer minPoolSize;

    @Value("${database-sql.max-statements}")
    private Integer maxStatements;

    @Bean
    public DataSource dataSource() {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClassName);
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Error configuring driverClass on dataSource! driverClassName=" + driverClassName, e);
        }
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setAcquireIncrement(acquireIncrement);
        dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxStatements(maxStatements);

        return dataSource;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Autowired
    public SessionFactory sessionSkillFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
        builder.scanPackages("org.mc536.webservice.domain.model.entity");
        builder.setProperty("hibernate.hbm2ddl.auto", "validate");
        return builder.buildSessionFactory();
    }
}
