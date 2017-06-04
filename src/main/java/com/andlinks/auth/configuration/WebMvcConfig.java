package com.andlinks.auth.configuration;

import com.andlinks.auth.interceptor.UserTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * spring webMvc相关的设置
 * Created by 王凯斌 on 2017/4/25.
 */
@Configuration
@EnableAutoConfiguration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UserTokenInterceptor userTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * addInterceptor这个方法这里有个坑要注意，自定义的拦截器要依赖注入后再
         * 放入这个方法，否则拦截器里面的属性不会被spring自动注入。
         */
        registry.addInterceptor(userTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/common/**")
                .excludePathPatterns("*.html")
                .excludePathPatterns("/error")
                .excludePathPatterns("/authentication");
    }
}
