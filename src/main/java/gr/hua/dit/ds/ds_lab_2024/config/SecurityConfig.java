package gr.hua.dit.ds.ds_lab_2024.config;

import gr.hua.dit.ds.ds_lab_2024.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private UserService userService;

    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/register", "/js/**", "/css/**", "/realestate/estates").permitAll()
                        .requestMatchers("/tenant/tenant").hasRole("TENANT")
                        .requestMatchers("/realestate/new").hasRole("RENTER")
                        .requestMatchers("/admin/**", "/auth/users"," /tenant/tenant", "/realestate/new").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/") // No `true` parameter
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                .logout((logout) -> logout.permitAll());
        return http.build();
    }


}
