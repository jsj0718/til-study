package me.jsj.domain.user.dao.chapter5;

import me.jsj.domain.user.Level;
import me.jsj.domain.user.UserV2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDaoJdbcCh5V1 implements UserDaoCh5V1 {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<UserV2> userMapper = (rs, rowNum) ->
            new UserV2(rs.getString("id"), rs.getString("name"), rs.getString("password"), rs.getString("email"),
                    Level.valueOf(rs.getInt("level")), rs.getInt("login"), rs.getInt("recommend"));

    public UserDaoJdbcCh5V1(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(UserV2 user) {
        jdbcTemplate.update("insert into usersv2(id, name, password, email, level, login, recommend) values(?, ?, ?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    @Override
    public UserV2 get(String id) {
        return jdbcTemplate.queryForObject("select * from usersv2 where id = ?",
                new Object[]{id},
                userMapper);
    }

    @Override
    public List<UserV2> getAll() {
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
    public void update(UserV2 user) {
        jdbcTemplate.update(
                "update usersv2 set name = ?, password = ?, email = ?, level = ?, login = ?, recommend = ? where id = ?",
                user.getName(), user.getPassword(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId()
        );
    }
}
