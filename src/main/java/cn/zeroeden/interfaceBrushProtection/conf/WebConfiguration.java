package cn.zeroeden.interfaceBrushProtection.conf;

import cn.zeroeden.interfaceBrushProtection.interceptor.AccessLimintInterceptor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Zero
 * @time: 2023/2/13
 * @description:
 */

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public AccessLimintInterceptor createAccessLimintInterceptor(){
        return new AccessLimintInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createAccessLimintInterceptor())
                .addPathPatterns("/**");
//                .addPathPatterns("/pass/post")
//                .addPathPatterns("/refuse/get")
//                .addPathPatterns("/refuse/post");
    }

    /**
     * 改变RedisTemplate使用的反序列化组件
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om=new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); 好像过时了
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //字符串序列化方法
        template.setKeySerializer(new StringRedisSerializer());
        //Value序列化采用Jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
