package uz.pdp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.dto.UserRegistrationDto;
import uz.pdp.model.UserEntity;
import uz.pdp.repo.UserRepository;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(final UserRegistrationDto userRegistration){
        UserEntity user = new UserEntity();
        user.setUsername(userRegistration.username());
        user.setPassword(passwordEncoder.encode(userRegistration.password()));
        userRepository.save(user);
    }
}

