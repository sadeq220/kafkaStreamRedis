package com.example.coreMack;

import com.example.coreMack.model.AccountInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@SpringBootApplication
public class CoreMackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreMackApplication.class, args);
	}
	@Bean
	/**
	 * we use stand-alone connections
	 * you may use Master/replica connections in production
	 */
	public RedisStandaloneConfiguration redisStandaloneConfiguration(){
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName("localhost");
		redisStandaloneConfiguration.setPort(6379);
		return redisStandaloneConfiguration;
	}
	@Bean
	/**
	 * jedis connector to provide RedisConnectionFactory
	 * if you want reactive api you must use Lettuce connector which is netty-based connector
	 */
	public JedisConnectionFactory jedisConnectionFactory(){
		// fluent API
		JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().
				readTimeout(Duration.ofMillis(500L)).
				usePooling().
				build();
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration(),jedisClientConfiguration);
		return jedisConnectionFactory;
	}
	@Bean
	/**
	 * spring Template classes is a abstraction layer over low level api that use -- callback -- approach
	 * While 'RedisConnection' offers low-level methods that accept and return binary values (byte arrays),
	 * the template takes care of serialization and connection management, freeing the user from dealing with such details.
	 *
	 * RedisTemplate uses a Java-based serializer for most of its operations.
	 * This means that any object written or read by the template is serialized and deserialized through Java(SERIAL_VERSION_UID)
	 *
	 * stringRedisTemplate for storing AccountInfo by AccountNo
	 */
	public StringRedisTemplate redisTemplate(){
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory());
		return stringRedisTemplate;
	}

}
