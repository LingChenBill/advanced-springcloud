package com.lc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 授权服务器配置。
 *
 * @author lingchenbill
 * @description:
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final static String RESOURCE_ID = "user";

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 配置一个客户端
     * 即可以通过授权码类型获取令牌，也可以通过密码类型获取令牌。
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                // 客户端ID
                .withClient("client")
                // 客户端可以使用的授权类型
                .authorizedGrantTypes("authorization_code", "password", "refresh_token", "implicit")
                // 允许请求范围
                .scopes("all")
                // 客户端安全码
                .secret("secret")
                // 回调地址（第三方客户端的IP地址）
                .redirectUris("http://152.136.104.216:8888/");
    }

    /**
     * 配置AuthorizationServer tokenServices.
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        // 将令牌保存到内存中，也可以保存到数据库中，或者是Redis中
        endpoints.tokenStore(new InMemoryTokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false);

    }

    /**
     * 配置JWT转换器。
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 设置JWT的签名的密钥为：secret
        jwtAccessTokenConverter.setSigningKey("secret");

        return jwtAccessTokenConverter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();

    }

}
