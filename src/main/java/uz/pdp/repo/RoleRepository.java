package uz.pdp.repo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.model.UserRole;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Set<UserRole> getAllByUserId(final Long userId) {
        return new HashSet<>(jdbcTemplate.query("SELECT r.* FROM user_roles INNER JOIN role r on r.id = user_roles.role_id WHERE user_id = ?",
                new BeanPropertyRowMapper<>(UserRole.class),
                userId
        ));
    }
}
