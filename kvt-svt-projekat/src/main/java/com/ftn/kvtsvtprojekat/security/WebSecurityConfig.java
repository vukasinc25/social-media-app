package com.ftn.kvtsvtprojekat.security;

import com.ftn.kvtsvtprojekat.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Autowired
    private TokenUtils tokenUtils;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
        http.authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/group/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/group/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/group/*").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/group/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/post/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/post/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/post/*").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/post/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/clubs/{id}/**").access("@webSecurity.checkClubId(authentication,request,#id)")
                // ukoliko ne zelimo da koristimo @PreAuthorize anotacije nad metodama kontrolera, moze se iskoristiti hasRole() metoda da se ogranici
                // koji tip korisnika moze da pristupi odgovarajucoj ruti. Npr. ukoliko zelimo da definisemo da ruti 'admin' moze da pristupi
                // samo korisnik koji ima rolu 'ADMIN', navodimo na sledeci nacin:
                //.antMatchers("/api/clubs").hasRole("ADMIN")// ili .antMatchers("/admin").hasAuthority("ROLE_ADMIN")

                .anyRequest().authenticated().and()
                .cors().and()

                // umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
                .addFilterBefore(new AuthenticationTokenFilter(userDetailsService(), tokenUtils), BasicAuthenticationFilter.class);

        http.csrf().disable();
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

//    // metoda u kojoj se definisu putanje za igorisanje autentifikacije
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(HttpMethod.POST, "/api/users/login")
//                .requestMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico",
//                        "/**/*.html", "/**/*.css", "/**/*.js");
//
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.GET, "/group", "post");
    }
}
