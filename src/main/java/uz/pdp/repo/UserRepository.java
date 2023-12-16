package uz.pdp.repo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import uz.pdp.model.UserEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserRepository {
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate parameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.parameterJdbcTemplate = parameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserEntity> getByUsername(final String username) {
        try {
            return Optional.ofNullable(parameterJdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE username=:username",
                    Map.of("username", username),
                    new BeanPropertyRowMapper<>(UserEntity.class)
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void save(final UserEntity userEntity) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterJdbcTemplate.update(
                "INSERT INTO users(username, password) VALUES(:username, :password)",
                new MapSqlParameterSource()
                        .addValue("username", userEntity.getUsername())
                        .addValue("password", userEntity.getPassword())
                , keyHolder, new String[]{"id"}
        );
        parameterJdbcTemplate.update(
                "INSERT INTO user_roles(user_id) VALUES(:user_id)",
                Map.of("user_id", keyHolder.getKey().longValue())
        );
    }

    public Optional<UserEntity> getById(final long id) {
        try {
            return Optional.ofNullable(parameterJdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE id = :id",
                    Map.of("id", id),
                    new BeanPropertyRowMapper<>(UserEntity.class)
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<UserEntity> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(UserEntity.class));
    }
}

