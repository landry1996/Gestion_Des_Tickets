package com.landry.gestion_des_tickets.users.configs;

import com.landry.gestion_des_tickets.users.auditing.ApplicationAuditAware;
import com.landry.gestion_des_tickets.users.dao.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    public static final String USER_NOT_FOUND = "User not found";

    private final UsersRepository usersRepository;


    @Bean
    public UserDetailsService userDetailsService(){

        return username -> usersRepository.findUsersByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider auhtProvider = new DaoAuthenticationProvider();
        auhtProvider.setUserDetailsService(userDetailsService());
        auhtProvider.setPasswordEncoder(passwordEncoder());
        return auhtProvider;

    }


    @Bean
    public AuditorAware<Long> auditorAware(){
        return new ApplicationAuditAware();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
