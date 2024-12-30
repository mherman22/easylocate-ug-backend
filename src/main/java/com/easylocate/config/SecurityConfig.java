package com.easylocate.config;

import com.easylocate.filter.JWTFilter;
import com.easylocate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up application security.
 * <p>
 * Configures authentication providers, password encoders, and the HTTP security chain.
 * </p>
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JWTFilter jwtFilter;

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param httpSecurity the {@link HttpSecurity} object to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs while configuring the security chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("Configuring SecurityFilterChain");

        return httpSecurity
                .csrf().disable() // Consider re-enabling and handling CSRF for stateless JWT tokens
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/api/user/register", "/api/user/login", "/h2-console/**", "/public/**", "/oauth2/**").permitAll()
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions().disable()) // Disable this only for development
                .httpBasic()
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/login/oauth2/code/*"))
                        .successHandler((request, response, authentication) ->
                                response.sendRedirect("/oauth2/login-success"))
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Configures the authentication provider, specifying the user details service and password encoder.
     *
     * @return the configured {@link AuthenticationProvider}
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        logger.info("Setting up DaoAuthenticationProvider");
        return provider;
    }

    /**
     * Provides the authentication manager using the authentication configuration.
     *
     * @param authenticationConfiguration the {@link AuthenticationConfiguration} used to create the manager
     * @return the configured {@link AuthenticationManager}
     * @throws Exception if an error occurs while creating the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures the password encoder to use BCrypt hashing.
     *
     * @return the configured {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Setting up BCryptPasswordEncoder");
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        logger.info("Configuring Google Ouath2Login");
        return ClientRegistration.withRegistrationId("google")
                .clientId("41886049452-uim7vr46cu44522ilc8t30lhs8j91uhl.apps.googleusercontent.com")
                .clientSecret("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")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("openid", "profile", "email", "address", "phone")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();
    }
}
