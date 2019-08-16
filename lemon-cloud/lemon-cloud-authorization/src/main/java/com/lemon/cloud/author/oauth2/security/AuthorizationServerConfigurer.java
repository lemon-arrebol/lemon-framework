package com.lemon.cloud.author.oauth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @param
 * @author lemon
 * @description 配置授权中心信息
 * @return
 * @date 2019-08-11 23:02
 */
@Configuration
// 开启认证授权中心
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApprovalStore approvalStore;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private OAuth2AccessDeniedHandler oauth2AccessDeniedHandler;

    @Autowired
    private OAuth2AuthenticationEntryPoint oauth2AuthenticationEntryPoint;

    /**
     * @param
     * @return
     * @description 配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，
     * 能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     * @author lemon
     * @date 2019-08-11 23:04
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    /**
     * @param
     * @return
     * @description 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)；
     * @author lemon
     * @date 2019-08-12 23:02
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // reuseRefreshTokens设置为false时，每次通过refresh_token获得access_token时，也会刷新refresh_token；也就是说，会返回全新的access_token与refresh_token。
        // 默认值是true，只返回新的access_token，refresh_token不变。
//        endpoints
//                .approvalStore(this.approvalStore)
//                .authenticationManager(authenticationManager)
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

        endpoints
                .userDetailsService(this.userDetailsService)
                .userApprovalHandler(this.userApprovalHandler)
                .approvalStore(this.approvalStore)
                .authorizationCodeServices(this.authorizationCodeServices)
                .authenticationManager(this.authenticationManager)
                .tokenStore(this.tokenStore);

        // 配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // 1天
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
        endpoints.tokenServices(tokenServices);
    }

    /**
     * @param
     * @return
     * @description 配置令牌端点(Token Endpoint)的安全约束
     * @author lemon
     * @date 2019-08-12 23:06
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
//        security
//                // 允许表单认证
//                .allowFormAuthenticationForClients()
////                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()");

        security
                .accessDeniedHandler(this.oauth2AccessDeniedHandler)
                .authenticationEntryPoint(this.oauth2AuthenticationEntryPoint)
                .allowFormAuthenticationForClients();
    }
}