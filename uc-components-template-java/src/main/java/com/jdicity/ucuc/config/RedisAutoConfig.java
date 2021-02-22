package com.jdicity.ucuc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * RedisAutoConfig: 缓存插件.
 *
 * @author liyingda
 * @date 2020-11-19 18:14
 * @version v1.0.0
 */
@Configuration
@Profile("!unitTest")
public class RedisAutoConfig {
    /**
     * 缓存插件实现.
     *
     * @param redisConnectionFactory redis 连接池.
     * @return redis实例.
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        template.setValueSerializer(redisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
