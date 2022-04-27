package com.example.coreMack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.SimpleTransactionFactory;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.Random;

@EnableTransactionManagement
/**
 * enable declarative transaction management
 */
@SpringBootApplication
/**
 * NoSQL storage systems provide an alternative to classical RDBMS for horizontal scalability and speed.
 * redis in-memory key-value store .keys are always string and value type could be {string , list , set , hash , orderedSet , stream}
 */
@EnableRedisRepositories
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
	 * spring Template classes is a abstraction layer over low level api that use -- callback -- approach ,
	 * these classes implement 'Template method pattern',
	 * In a nutshell, this pattern defines the skeleton of an algorithm as an abstract class,
	 * allowing its subclasses to provide concrete behavior.
	 * a template method that makes a series of method calls in a specific order to perform the steps of an algorithm.
	 * in case of RedisTemplate it will get connection, do the exception translations , ... , release connection as an ordered steps.
	 *
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
		stringRedisTemplate.setEnableTransactionSupport(true);
		/**
		 *  Doing so forces binding the current RedisConnection to the current Thread that is triggering MULTI
		 */
		return stringRedisTemplate;
	}
	@Bean
	public DataSource dataSource(){
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		embeddedDatabaseBuilder.setName("ForTransaction");
		embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.H2);
		return embeddedDatabaseBuilder.build();
	}

	@Bean
	/**
	 * TransactionManager suppose to bind a resource(e.g. JDBC connection,Hibernate Session ) to a thread
	 *
	 * Transaction management requires a PlatformTransactionManager.
	 * Spring Data Redis does not ship with a PlatformTransactionManager implementation.
	 * Assuming your application uses JDBC, Spring Data Redis can participate in transactions by using existing transaction managers.
	 */
	public PlatformTransactionManager platformTransactionManager(){
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public Random random(){
		return new Random();
	}

}
