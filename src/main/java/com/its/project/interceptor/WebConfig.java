package com.its.project.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheckInterceptor())
            .order(1)
            .addPathPatterns("/**", "/board/save-form")
            .excludePathPatterns("/", "/member/save-form", "/member/save", "/member/duplicateCheck",
                    "/member/login-form", "/member/login", "/board/search", "/board/",
                    "/js/**", "/css/**", "/*.ico", "/error", "/images/**", "/favicon/**");
  }
}
