package com.xxm.syncbase.core.security.config;

import com.xxm.syncbase.core.security.service.implementation.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class SecurityConfig {
    public SecurityConfig() {
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class ApplicationUserConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationSuccessHandler authenticationSuccessHandler;
        @Autowired
        private AuthenticationFailureHandler authenticationFailureHandler;
        @Autowired
        private CustomAuthenticationProvider customAuthenticationProvider;

        public ApplicationUserConfigurationAdapter() {
        }

        @Bean
        public SessionRegistry sessionRegistry() {
            return new SessionRegistryImpl();
        }

        protected void configure(AuthenticationManagerBuilder authenticationBuilder) throws Exception {
            authenticationBuilder.authenticationProvider(this.customAuthenticationProvider);
        }

        protected void configure(HttpSecurity httpSecurity) throws Exception {
            ((HttpSecurity)((HttpSecurity)((HttpSecurity)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)httpSecurity.antMatcher("/**").authorizeRequests().anyRequest()).fullyAuthenticated().and()).formLogin().loginPage("/login").permitAll()).loginProcessingUrl("/login/process")).defaultSuccessUrl("/dashboard", true)).failureUrl("/login?failed=true")).successHandler(this.authenticationSuccessHandler)).failureHandler(this.authenticationFailureHandler)).and()).logout().logoutUrl("/logout").logoutSuccessUrl("/").deleteCookies(new String[]{"JSESSIONID"}).invalidateHttpSession(true).and()).csrf().disable()).sessionManagement().invalidSessionUrl("/login").maximumSessions(1).expiredUrl("/login?expired=true").sessionRegistry(this.sessionRegistry()).and().sessionFixation().migrateSession().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        }

        public void configure(WebSecurity webSecurity) throws Exception {
            webSecurity.ignoring().antMatchers(new String[]{"/error**", "/login**", "/actuator/**", "/resources/**", "/static/**", "/assets/**", "/fonts/**", "/css/**", "/js/**", "/img/**", "/images/**", "/actuator/**", "/integration/**"});
        }
    }
}