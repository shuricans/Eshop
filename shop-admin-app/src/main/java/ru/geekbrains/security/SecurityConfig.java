package ru.geekbrains.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**/*.css", "/**/*.js").permitAll()
                .antMatchers("/product").permitAll()
                .antMatchers("/product/**").hasAnyRole("MANAGER", "ADMIN", "SUPER_ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .antMatchers("/user/**").hasRole("SUPER_ADMIN")
                .antMatchers("/login*").permitAll()
                .anyRequest()
                .authenticated()
                    .and()
                        .formLogin()
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/product", true)
                    .and()
                        .logout()
                        .permitAll()
                    .and()
                        .exceptionHandling()
                        .accessDeniedPage("/access_denied");
    }
}
