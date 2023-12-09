package uz.pdp.config.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.pdp.model.UserAuth;
import uz.pdp.repo.UserAuthRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    public CustomUserDetailsService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserAuth userAuth = userAuthRepository.getByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username - " + username + " , not found.")
        );
        return User.builder()
                .username(userAuth.getUsername())
                .password(userAuth.getPassword())
                .roles(userAuth.getRole())
                .build();
    }
}
