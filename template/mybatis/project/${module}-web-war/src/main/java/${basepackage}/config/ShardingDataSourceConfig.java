package ${basepackage}.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.MasterSlaveDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.config.common.api.config.ShardingRuleConfig;
import com.dangdang.ddframe.rdb.sharding.config.common.api.config.StrategyConfig;
import com.dangdang.ddframe.rdb.sharding.config.common.api.config.TableRuleConfig;
import com.dangdang.ddframe.rdb.sharding.spring.datasource.SpringShardingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 16/8/2 下午5:04<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@Configuration
@EnableAutoConfiguration
public class ShardingDataSourceConfig {

    @Value("<@jspEl 'spring.datasource.driver-class-name' />")
    String driverClassName;

    @Value("<@jspEl 'spring.datasource.url' />")
    String url;

    @Value("<@jspEl 'spring.datasource.username' />")
    String username;

    @Value("<@jspEl 'spring.datasource.password' />")
    String password;

    @Value("<@jspEl 'spring.datasource.slave.driver-class-name' />")
    String slaveDriverClassName;

    @Value("<@jspEl 'spring.datasource.slave.url' />")
    String slaveUrl;

    @Value("<@jspEl 'spring.datasource.slave.username' />")
    String slaveUsername;

    @Value("<@jspEl 'spring.datasource.slave.password' />")
    String slavePassword;


    @Bean(name = "msDatasource")
    public DataSource initShardingDataSource(){
        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setDriverClassName(driverClassName);
        masterDataSource.setUrl(url);
        masterDataSource.setUsername(username);
        masterDataSource.setPassword(password);

        DruidDataSource slaveDataSource = new DruidDataSource();
        slaveDataSource.setDriverClassName(slaveDriverClassName);
        slaveDataSource.setUrl(slaveUrl);
        slaveDataSource.setUsername(slaveUsername);
        slaveDataSource.setPassword(slavePassword);

        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("msDataSource", MasterSlaveDataSourceFactory.createDataSource("msds", masterDataSource, slaveDataSource));

        StrategyConfig databaseStrategy = new StrategyConfig();
        databaseStrategy.setAlgorithmExpression("msds");
        databaseStrategy.setShardingColumns("user_id");

        TableRuleConfig tableRuleConfig = new TableRuleConfig();
        tableRuleConfig.setDatabaseStrategy(databaseStrategy);
        tableRuleConfig.setActualTables("mock");

        Map<String, TableRuleConfig> tables = new HashMap<>();
        tables.put("mock", tableRuleConfig);

        ShardingRuleConfig shardingRuleConfig = new ShardingRuleConfig();
        shardingRuleConfig.setDataSource(dataSourceMap);
        shardingRuleConfig.setTables(tables);

        SpringShardingDataSource springShardingDataSource = new SpringShardingDataSource(shardingRuleConfig, new Properties());


        return springShardingDataSource;
    }

    private static Map<String, DataSource> createDataSourceMap(DataSource slaveDataSource) {
        Map<String, DataSource> result = new HashMap<>(2);
        result.put("ds_0", slaveDataSource);
        result.put("ds_1", slaveDataSource);
        return result;
    }
}