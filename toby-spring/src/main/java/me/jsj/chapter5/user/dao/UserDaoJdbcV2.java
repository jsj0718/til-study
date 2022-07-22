package me.jsj.chapter5.user.dao;

import me.jsj.chapter5.user.domain.Level;
import me.jsj.chapter5.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDaoJdbcV2 implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = (rs, rowNum) ->
            new User(rs.getString("id"), rs.getString("name"), rs.getString("password"), rs.getString("email"),
                    Level.valueOf(rs.getInt("level")), rs.getInt("login"), rs.getInt("recommend"));

    public UserDaoJdbcV2(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(User user) {
        jdbcTemplate.update("insert into usersv2(id, name, password, email, level, login, recommend) values(?, ?, ?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    @Override
    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from usersv2 where id = ?",
                new Object[]{id},
                userMapper);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from usersv2 order by id",
                userMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from usersv2");
    }

    @Override
    public int getCount() {
        return jdbcTemplate.query(con -> con.prepareStatement("select count(*) from usersv2"), rs -> {
            rs.next();
            return rs.getInt(1);
        });
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                "update usersv2 set name = ?, password = ?, email = ?, level = ?, login = ?, recommend = ? where id = ?",
                user.getName(), user.getPassword(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId()
        );
    }
}
