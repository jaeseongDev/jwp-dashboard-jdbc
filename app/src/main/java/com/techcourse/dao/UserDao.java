package com.techcourse.dao;

import com.techcourse.domain.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import nextstep.jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }

            @Override
            protected String createQuery() {
                return "insert into users (account, password, email) values (?, ?, ?)";
            }

            @Override
            protected DataSource getDataSource() {
                return UserDao.this.getDataSource();
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getAccount());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getEmail());
            }
        };
        jdbcTemplate.update();
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }

            @Override
            protected String createQuery() {
                return "update users SET account=?, password=?, email=? where id =?";
            }

            @Override
            protected DataSource getDataSource() {
                return UserDao.this.getDataSource();
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getAccount());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getEmail());
                pstmt.setLong(4, user.getId());
            }
        };
        jdbcTemplate.update();
    }

    public List<User> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected Object mapRow(ResultSet rs) throws SQLException {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
                }
                return users;
            }

            @Override
            protected String createQuery() {
                return "select id, account, password, email from users";
            }

            @Override
            protected DataSource getDataSource() {
                return UserDao.this.getDataSource();
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {

            }
        };
        return (List<User>) jdbcTemplate.query();
    }

    public User findById(Long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected Object mapRow(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                }
                return null;
            }

            @Override
            protected String createQuery() {
                return "select id, account, password, email from users where id = ?";
            }

            @Override
            protected DataSource getDataSource() {
                return UserDao.this.getDataSource();
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setLong(1, id);
            }
        };
        return (User) jdbcTemplate.query();
    }

    public User findByAccount(String account) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected Object mapRow(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                }
                return null;
            }

            @Override
            protected String createQuery() {
                return "select id, account, password, email from users where account = ?";
            }

            @Override
            protected DataSource getDataSource() {
                return UserDao.this.getDataSource();
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, account);
            }
        };
        return (User) jdbcTemplate.query();
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
