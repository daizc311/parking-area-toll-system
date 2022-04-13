package run.bequick.dreamccc.pats.config;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {


    @Bean
    Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            // 序列化时过滤jpa实体额外的字段
            SimpleBeanPropertyFilter fieldFilter = SimpleBeanPropertyFilter.serializeAllExcept("new", "hibernateLazyInitializer");
            SimpleFilterProvider filterProvider = new SimpleFilterProvider();
            filterProvider.setDefaultFilter(fieldFilter);
            jacksonObjectMapperBuilder.filters(filterProvider);
            jacksonObjectMapperBuilder.failOnEmptyBeans(false);
            jacksonObjectMapperBuilder.failOnUnknownProperties(false);
        };
    }

}
