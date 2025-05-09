package com.study2.demo.config;

import com.study2.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginService loginService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/post", true)
                    .failureUrl("/login?error")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/upload-dir/**").permitAll()  // ✅ 정적 리소스 허용
                    .requestMatchers("/login", "/register").permitAll()  // 로그인, 회원가입도 허용
                    .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
