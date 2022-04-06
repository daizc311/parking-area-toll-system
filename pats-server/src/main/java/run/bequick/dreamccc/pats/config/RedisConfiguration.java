package run.bequick.dreamccc.pats.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import run.bequick.dreamccc.pats.param.ThreeFactor;

@Configuration
public class RedisConfiguration {

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {

        var template = new RedisTemplate<String, Object>();
        // 配置连接工厂
        template.setConnectionFactory(factory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        var serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        serializer.setObjectMapper(objectMapper);
        // 值采用json序列化
        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    RedisTemplate<String, ThreeFactor> verifyThreeFactorTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {

        var template = new RedisTemplate<String, ThreeFactor>();
        template.setConnectionFactory(factory);
        var serializer = new Jackson2JsonRedisSerializer<>(ThreeFactor.class);
        serializer.setObjectMapper(objectMapper);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }


}
