package indi.shine.web.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author xiezhenxiang 2021/9/15
 */
public class BizWebMvcConfigurer implements WebMvcConfigurer {


    @Value("${filter.cross:false}")
    private Boolean filterCross;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 将json处理的转换器放到第一位
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void addCorsMappings(@Nonnull CorsRegistry registry) {
        if (filterCross) {
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedHeaders("*")
                    .allowedMethods("*")
                    // 预检请求的有效时间
                    .maxAge(3600)
                    //是否允许发送Cookie
                    .allowCredentials(true);
        }
    }
}
