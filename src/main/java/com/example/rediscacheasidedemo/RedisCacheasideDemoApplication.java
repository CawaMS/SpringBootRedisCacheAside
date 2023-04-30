package com.example.rediscacheasidedemo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@EnableCaching
public class RedisCacheasideDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisCacheasideDemoApplication.class, args);
	}

	@Bean
	public LettuceConnectionFactory redisStandAloneConnectionFactory() {
		//indicate SSL is used if so in the config object
		LettuceClientConfiguration config = LettuceClientConfiguration.builder().useSsl().build();
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("your_azure_redis_cache_name.redis.cache.windows.net",6380);
		redisStandaloneConfiguration.setPassword("your_redis_password");
		redisStandaloneConfiguration.setDatabase(0);
		return new LettuceConnectionFactory(redisStandaloneConfiguration, config);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplateStandAlone(@Qualifier("redisStandAloneConnectionFactory")LettuceConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}
