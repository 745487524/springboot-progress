package com.chinatop.contains.error;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ErrorPageConfig {

    @Bean
    public WebServerFactoryCustomizer containerCustomuzer() {
        return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {

            @Override
            public void customize(ConfigurableServletWebServerFactory factory) {
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
                Set<ErrorPage> errorPageSet = new HashSet<>();
                errorPageSet.add(error404Page);
                factory.setErrorPages(errorPageSet);
            }
        };
    }
}
