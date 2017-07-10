package nju.edu.cn.qaserver.apiGateway.configuration;

import nju.edu.cn.qaserver.apiGateway.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by nju.edu.cn.qaserver on 2017/6/28.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationFilter authenticationFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 使用JWT不需要csrf
                .csrf().disable()
                // 基于token不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // login route is only publicly available for POST requests
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/refresh").permitAll()
                .anyRequest().authenticated()
                .and()
                // And filter other requests to check the presence of JWT in header
                // 集成JWT和Spring Security
                // 如果客户端请求体中包含token，在检查token之后才放行
                .addFilterBefore(authenticationFilterBean(),
                        UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Create a default apiGateway
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("password")
                .roles("ADMIN");
    }
}
