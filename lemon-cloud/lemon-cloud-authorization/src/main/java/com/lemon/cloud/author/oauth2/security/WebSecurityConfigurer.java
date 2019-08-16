package com.lemon.cloud.author.oauth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    protected UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder);
    }

    /**
     * @param
     * @return
     * @description 拦截所有请求, 使用httpBasic方式登陆
     * @author lemon
     * @date 2019-08-11 23:11
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/**")
//                .fullyAuthenticated()
//                // 拦截所有请求 通过httpBasic进行认证
//                .and().httpBasic();

//        http
//                // 登录界面，默认是permit All
//                .formLogin()
//                .and()
//                // 不用身份认证可以访问
//                .authorizeRequests().antMatchers("/", "/home").permitAll()
//                .and()
//                // 其它的请求要求必须有身份认证
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .authorizeRequests().antMatchers("/oauth/**").permitAll();

        http
                .authorizeRequests()
                .antMatchers("/source/**").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    /**
     * @param
     * @return
     * @description 无法注入 使用 这种方式 授权中心管理器
     * @author lemon
     * @date 2019-08-11 23:12
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public AuthenticationProvider daoAuhthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(super.userDetailsService());
//        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
//        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
//        return daoAuthenticationProvider;
//    }
}