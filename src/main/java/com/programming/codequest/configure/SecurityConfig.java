package com.programming.codequest.configure;

import com.programming.codequest.security.CustomUserDetailsServiceImpl;
import com.programming.codequest.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                // NEW: allow frontend requests.
                .cors(cors -> {})
                .authenticationProvider(authenticationProvider())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/Users/register",
                                "/Users/login",
                                "/submissions/callback"
                                ).permitAll()

                        .requestMatchers(org.springframework.http.HttpMethod.GET,"/problems/**").authenticated()

                        // ONLY ADMIN can create/update/delete problems
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/problems/**")
                        .hasRole("ADMIN")

                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/problems/**")
                        .hasRole("ADMIN")

                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/problems/**")
                        .hasRole("ADMIN")

                        // ONLY ADMIN can manage test cases
                        .requestMatchers("/testcases/**")
                        .hasRole("ADMIN")

                        // Logged-in users can submit solutions
                        .requestMatchers("/submissions/submit")
                        .authenticated()

                        // Logged-in users can view submissions
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/submissions/**")
                        .authenticated()

                        .anyRequest().denyAll()
                )


                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
/* This is completely new code.
It tells Spring Boot:
Frontend:
http://127.0.0.1:5500

        ↓

Spring Boot:
http://localhost:8080
and allows the frontend to communicate with the backend.
 */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://127.0.0.1:5500")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}