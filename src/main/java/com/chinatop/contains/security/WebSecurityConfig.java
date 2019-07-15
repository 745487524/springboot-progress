package com.chinatop.contains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 描述：security配置类
 *
 * @author ay
 * @date 2017/12/10.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ACCESS_PREFIX = "ROLE_";

    @Bean
    public CustomUserService customUserService() {
        return new CustomUserService();
    }

    //    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return new BCryptPasswordEncoder();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //路由策略和访问权限的简单配置
        String access_token = ACCESS_PREFIX + "ADMIN";
        http
                .authorizeRequests()
//                .antMatchers("").access("hasRole('ADMIN')")
                .antMatchers("/actuator", "/actuator/**").access("hasRole('ADMIN')")
                .antMatchers("/**")
//                .access("hasRole('ADMIN')")
//                .antMatchers("/**")
                .permitAll()
                .and()
                .formLogin()
                .failureUrl("/login?error")
                .defaultSuccessUrl("/ayUser/test")
                .permitAll();
        //登陆页面全部权限可访问
        super.configure(http);
    }

    /**
     * 配置内存用户
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
//                .passwordEncoder(new BCryptPasswordEncoder());
//            .inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//            .withUser("阿毅").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN")
//            .and()
//            .withUser("阿兰").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
    }

}