package me.jsj.domain.user.dao.chapter4;

import me.jsj.domain.user.UserV1;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDaoJdbcCh4V1 implements UserDaoCh4V1 {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<UserV1> userMapper = (rs, rowNum) -> new UserV1(rs.getString("id"), rs.getString("name"), rs.getString("password"));

    public UserDaoJdbcCh4V1(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(UserV1 user) {
        jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    @Override
    public UserV1 get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                userMapper);
    }

    @Override
    public List<UserV1> getAll() {
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
