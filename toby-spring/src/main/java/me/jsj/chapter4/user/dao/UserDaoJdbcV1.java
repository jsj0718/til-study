package me.jsj.chapter4.user.dao;

import me.jsj.chapter1.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDaoJdbcV1 implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

    public UserDaoJdbcV1(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(User user) {
        jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    @Override
    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                userMapper);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id",
                userMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    @Override
    public int getCount() {
        return jdbcTemplate.query(con -> con.prepareStatement("select count(*) from users"), rs -> {
            rs.next();
            return rs.getInt(1);
        });
    }
}
