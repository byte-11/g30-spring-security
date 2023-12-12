package uz.pdp.config.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.pdp.model.UserEntity;
import uz.pdp.model.UserRole;
import uz.pdp.repo.PermissionRepository;
import uz.pdp.repo.RoleRepository;
import uz.pdp.repo.UserRepository;

import java.util.Arrays;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.getByUsername(username).orElseThrow(
                () -> new IllegalStateException("Username - " + username + " , not found.")
        );
        Set<UserRole> roles = roleRepository.getAllByUserId(userEntity.getId());
        for (UserRole userRole : roles) {
            userRole.setPermissions(permissionRepository.getAllByRoleId(userRole.getId()));
        }
        userEntity.setRoles(roles);

        return new UserContext(userEntity);
    }
}
