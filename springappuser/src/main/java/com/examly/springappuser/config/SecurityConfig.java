package com.examly.springappuser.config;
import java.beans.BeanProperty;

import javax.management.RuntimeErrorException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> crsf.disable())
        .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                    .anyRequest().authenticated()
                    )
                    .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                    .sessionManagement(session -> session.sessionCreationPolicy(sessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        try{
            return config.getAuthenticationManager();
        }
        catch(Exception e){
            throw new RuntimeErrorException("Failed to get AuthenticationManager",e);

        }
    }

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    
}
