package com.myapp.web.springboot.config;

import com.myapp.web.springboot.config.auth.LoginUserArgmentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgmentResolver loginUserArgmentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){ // HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentResolvers를 통해 추가해야함  
        argumentResolvers.add(loginUserArgmentResolver);
    }

}
