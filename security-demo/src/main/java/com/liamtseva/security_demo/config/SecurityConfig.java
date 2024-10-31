package com.liamtseva.security_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> {
          // Доступ для всіх
          auth.requestMatchers("/", "/register", "/login", "/favicon.ico").permitAll();
          auth.requestMatchers("/userHome").authenticated(); // Доступ до сторінки userHome лише для аутентифікованих користувачів
          auth.anyRequest().authenticated(); // Всі інші запити вимагають аутентифікації
        })
        .formLogin(form -> form
            .loginPage("/login") // Налаштування сторінки входу
            .defaultSuccessUrl("/userHome")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/") // Куди перенаправити після виходу
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll()
        )
        .oauth2Login(oauth -> oauth
            .loginPage("/login") // Налаштування сторінки входу для OAuth2
            .defaultSuccessUrl("/userHome") // Перенаправлення після успішної аутентифікації
            .permitAll()
        );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
