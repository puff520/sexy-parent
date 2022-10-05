package com.ikun.config;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puff
 * @desc Redisson 配置
 */
@Configuration
public class RedissonConfig {


    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonClient redisson() {
        return Redisson.create(singleConfiguration());
    }


    private Config singleConfiguration() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + redissonProperties.getHost() + ":" + redissonProperties.getPort());
        String password = redissonProperties.getPassword();
        if (StrUtil.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        return config;
    }


}
