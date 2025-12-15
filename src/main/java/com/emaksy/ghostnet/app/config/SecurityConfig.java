package com.emaksy.ghostnet.app.config;

import com.emaksy.ghostnet.app.security.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/css/**", "/js/**", "/images/**", "/webjars/**", "/signup", "/login")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/", "/report", "/recoveries", "/map", "/tasks")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/home")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/", "/report", "/signup")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
        .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(
      AppUserDetailsService appUserDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(appUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }
}
