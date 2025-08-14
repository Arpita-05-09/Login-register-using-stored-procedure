package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerUser(String username, String password) {
        jdbcTemplate.update("CALL register_user(?, ?)", username, password);
    }

    public User loginUser(String username, String password) {
        try {
            return jdbcTemplate.queryForObject(
                    "CALL login_user(?, ?)",
                    new Object[]{username, password},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return new User(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("password")
                            );
                        }
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
