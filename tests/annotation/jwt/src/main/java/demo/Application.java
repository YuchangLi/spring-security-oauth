package demo;

import java.security.KeyPair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@SpringBootApplication
@EnableResourceServer
@RestController
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @RequestMapping("/")
  public String home() {
    return "Hello World";
  }

  @RequestMapping("/hello")
  public String hello() {
    return "Hello World he";
  }

  @Bean
  public JwtTokenStore tokenStore() throws Exception {
    JwtAccessTokenConverter enhancer = new JwtAccessTokenConverter();
//    // N.B. in a real system you would have to configure the verifierKey (or use JdbcTokenStore)
//    enhancer.afterPropertiesSet();
    return new JwtTokenStore(enhancer);
  }


  @Configuration
  @EnableAuthorizationServer
  protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
      // return new JwtAccessTokenConverter();
      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
      KeyPair keyPair =
          new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "foobar".toCharArray())
              .getKeyPair("test");
      converter.setKeyPair(keyPair);
      return converter;

    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
      oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
          .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.authenticationManager(authenticationManager)
          .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      // @formatter:off
      clients.inMemory().withClient("my-trusted-client")
          .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
          .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read", "write", "trust")
          .accessTokenValiditySeconds(60).refreshTokenValiditySeconds(160)
          .redirectUris("http://localhost:8081/", "http://localhost:8081/client/",
              "http://localhost:8081/client/hello", "http://localhost:8081/hello")
          // .secret("secret")
          .and().withClient("my-client-with-registered-redirect")
          .authorizedGrantTypes("authorization_code").authorities("ROLE_CLIENT")
          .scopes("read", "trust").redirectUris("http://anywhere?key=value").and()
          .withClient("my-client-with-secret")
          .authorizedGrantTypes("client_credentials", "password")
          .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read", "write")
          .secret("secret");
      // @formatter:on
    }

  }

}
