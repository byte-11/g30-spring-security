package uz.pdp.repo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.model.UserAuth;

import java.util.Map;
import java.util.Optional;

@Component
public class UserAuthRepository {
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    public UserAuthRepository(NamedParameterJdbcTemplate parameterJdbcTemplate) {
        this.parameterJdbcTemplate = parameterJdbcTemplate;
    }

    public Optional<UserAuth> getByUsername(final String username) {
        try {
            return Optional.ofNullable(parameterJdbcTemplate.queryForObject(
                    "SELECT * FROM user_auth WHERE username=:username",
                    Map.of("username", username),
                    new BeanPropertyRowMapper<>(UserAuth.class)
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

