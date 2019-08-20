package com.olaa.avatar.open.gateway.config;

import java.lang.reflect.Method;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olaa.avatar.open.common.util.RedisUtil ;

//@Configuration
public class RedisConfig {
	
	public static final String CACHE_MANAGER= "cacheManager";
	
	public static final String CACHE_KEY_GENERATOR = "CacheKeyGenerator";
	
	
	@Bean
	@DependsOn("redisTemplate")
	public RedisUtil redisUtil(RedisTemplate<Object,Object> redisTemplate) {
		return new RedisUtil(redisTemplate);
	}

	/**
     * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类
     * @param factory
     * @return
     */
	@Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());//序列化KEY，不然key会乱码 /XX/XX fuck:依然会乱码
        template.setValueSerializer(getRedisConfig());
        template.afterPropertiesSet();
        return template;
    }
	
	
	@Bean
	public Jackson2JsonRedisSerializer<Object> getRedisConfig() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		return jackson2JsonRedisSerializer;
	}
	
	
	@Bean(name = CACHE_MANAGER )
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		// 配置序列化（解决乱码的问题）
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(10)) // 设置有效期10分钟
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getRedisConfig()))
				.disableCachingNullValues();// 不缓存空值
		return  RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();
    }
	
	@Bean(name = CACHE_KEY_GENERATOR )
    public KeyGenerator cacheKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object p : params) {
                	if( p != null ) sb.append(p.hashCode());
				}
                return sb.toString();
            }
        };
    }
	
}
