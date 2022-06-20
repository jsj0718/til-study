package me.jsj.chapter3.user.dao.v5;

import me.jsj.chapter1.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoV6 {
//    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

    public UserDaoV6(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
//        this.dataSource = dataSource;
    }

    public void deleteAll() {
        jdbcTemplate.update(con -> con.prepareStatement("delete from users"));
    }

    public void deleteAllV2() {
        jdbcTemplate.update("delete from users");
    }

    public void add(User user) {
        jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public int getCount() {
        return jdbcTemplate.query(con -> con.prepareStatement("select count(*) from users"), rs -> {
            rs.next();
            return rs.getInt(1);
        });
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                userMapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id",
                userMapper);
    }
}
