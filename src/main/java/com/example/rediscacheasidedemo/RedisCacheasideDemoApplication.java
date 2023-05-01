package com.example.rediscacheasidedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@EnableCaching
public class RedisCacheasideDemoApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(RedisCacheasideDemoApplication.class, args);
	}

	@Bean
	public LettuceConnectionFactory redisStandAloneConnectionFactory() {
		//indicate SSL is used if so in the config object
		LettuceClientConfiguration config = LettuceClientConfiguration.builder().useSsl().build();
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(environment.getProperty("Redis.HostName"),6380);
		redisStandaloneConfiguration.setPassword(environment.getProperty("Redis.Password"));
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
