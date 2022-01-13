package ssf.todoList.TodoList_project.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import ssf.todoList.TodoList_project.Constants;

@Configuration
public class RedisConfig {      
    private final static Logger logger = Logger.getLogger(RedisConfig.class.getName());

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Integer redisPort;
    @Value("${spring.redis.database}")
    private Integer redisDatabase;
    // @Value("${spring.redis.password}")
    // private String redisPassword;
    
    @Bean(Constants.TODO_REDIS)
    // @Scope("singleton")                         // Setting Redis Factory
    public RedisTemplate<String, String> createRedisTemplate() {      // Using <String, String> redistemplate
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        // logger.log(Level.INFO, "redis host port >> " + redisHost + " " + redisPort);
        
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(redisDatabase);

        final String redisPassword = System.getenv(Constants.REDIS_PASSWORD);
            if (redisPassword != null) {
                logger.log(Level.INFO, "Setting Redis password");
                config.setPassword(redisPassword);
            }
        
        final JedisClientConfiguration jediClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jediClient);
        jedisFac.afterPropertiesSet();
        //ogger.info("redis host port >> {redisHost} {redisPort}", redisHost, redisPort);

        RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());     // Convert value to String and encode to UTF-16
        
        return template;
    }
}
