package uz.pdp.repo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.model.RolePermission;

import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionRepository {
    private final JdbcTemplate jdbcTemplate;

    public PermissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Set<RolePermission> getAllByRoleId(final Long roleId){
        return new HashSet<>(jdbcTemplate.query(
                "SELECT p.* FROM role_permissions INNER JOIN permission p on p.id = role_permissions.permission_id WHERE role_id = ?",
                new BeanPropertyRowMapper<>(RolePermission.class),
                roleId
        ));
    }
}
