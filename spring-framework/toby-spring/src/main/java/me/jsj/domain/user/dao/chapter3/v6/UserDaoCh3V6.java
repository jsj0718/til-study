package me.jsj.domain.user.dao.chapter3.v6;

import me.jsj.domain.user.UserV1;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoCh3V6 {
//    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private RowMapper<UserV1> userMapper = (rs, rowNum) -> new UserV1(rs.getString("id"), rs.getString("name"), rs.getString("password"));

    public UserDaoCh3V6(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
//        this.dataSource = dataSource;
    }

    public void deleteAll() {
        jdbcTemplate.update(con -> con.prepareStatement("delete from users"));
    }

    public void deleteAllV2() {
        jdbcTemplate.update("delete from users");
    }

    public void add(UserV1 user) {
        jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public int getCount() {
        return jdbcTemplate.query(con -> con.prepareStatement("select count(*) from users"), rs -> {
            rs.next();
            return rs.getInt(1);
        });
    }

    public UserV1 get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                userMapper);
    }

    public List<UserV1> getAll() {
        return jdbcTemplate.query("select * from users order by id",
                userMapper);
    }
}
