package uz.pdp.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry ->
                                registry.requestMatchers("/auth/**", "/home").permitAll()
//                                .requestMatchers("/privilege/admins").hasAnyAuthority("ADMIN_DELETE", "ADMIN_CREATE")
//                                .requestMatchers("/privilege/users").hasAnyRole("ADMIN","USER")
//                                .requestMatchers("/privilege/managers").hasAuthority("MANAGER_CREATE")
                                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .formLogin(configurer -> configurer
                        .loginPage("/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", false)
                        .failureHandler(new CustomAuthenticationFailureHandler())
                )
                .logout(configurer -> configurer
                        .logoutUrl("/auth/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/home")
                )
                .rememberMe(configurer -> configurer
                        .rememberMeParameter("remember-me")
                        .rememberMeCookieName("ME")
                        .key("SecurityKey@cwpcAcnIOANICUNWASXCaxax")
                        .tokenValiditySeconds(60)
                        .userDetailsService(userDetailsService)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    @SessionScope
    public UserContext userContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (UserContext) authentication.getPrincipal();
        }
        return null;
    }
}
