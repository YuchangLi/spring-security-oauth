///**  
// * @Title: ResourceServerConfig.java
// * @Package demo.config
// * @author liyuchang
// * @date 2018年4月27日  
// */
//package demo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
///**
// * @ClassName: ResourceServerConfig
// * @Description: TODO(这里用一句话描述这个类的作用)
// * @author liyuchang
// * @date 2018年4月27日
// */
//@Configuration
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//  @Override
//  public void configure(ResourceServerSecurityConfigurer resources) {
//    resources.resourceId("resourceId").stateless(false);
//  }
//
//  @Override
//  public void configure(HttpSecurity http) throws Exception {
//    // @formatter:off
//    // http.authorizeRequests().anyRequest().authenticated();
//    http
//      .authorizeRequests()
//      .antMatchers("/resource/**").access("permitAll()")
//      .and()
//      .authorizeRequests().anyRequest().authenticated();
//        // Since we want the protected resources to be accessible in the UI as well we need
//        // session creation to be allowed (it's disabled by default in 2.0.6)
////    http
////        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
////        .requestMatchers()
////        .antMatchers("/photos/**", "/oauth/users/**", "/oauth/clients/**", "/me").and()
////        .authorizeRequests().antMatchers("/me").access("#oauth2.hasScope('read')")
////        .antMatchers("/photos")
////        .access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
////        .antMatchers("/photos/trusted/**").access("#oauth2.hasScope('trust')")
////        .antMatchers("/photos/user/**").access("#oauth2.hasScope('trust')")
////        .antMatchers("/photos/**")
////        .access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
////        .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
////        .access(
////            "#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
////        .regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
////        .access(
////            "#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
////        .regexMatchers(HttpMethod.GET, "/oauth/clients/.*").access(
////            "#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
//    // @formatter:on
//  }
//
//
//
//}
