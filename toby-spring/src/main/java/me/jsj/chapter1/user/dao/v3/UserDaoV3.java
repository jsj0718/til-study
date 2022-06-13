package me.jsj.chapter1.user.dao.v3;

import me.jsj.chapter1.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoV3 {
/*
    //클래스 분리 -> 문제점 존재
    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDaoV3() {
        simpleConnectionMaker = new SimpleConnectionMaker();
    }
*/

    //인터페이스 활용한 분리 -> 추상화, 확장성
    private ConnectionMaker connectionMaker;

/*
    //Connection 생성 책임이 UserDao에 존재 -> new 사용 X
    public UserDaoV3() {
        connectionMaker = new NConnectionMaker();
    }
*/

    //UserDao를 호출하는 외부 클라이언트에게 Connection 생성 책임을 맡김
    public UserDaoV3(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
//        Connection conn = simpleConnectionMaker.getConnection();
        Connection conn = connectionMaker.makeConnection();

        PreparedStatement ps = conn.prepareStatement("insert into USERS(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
//        Connection conn = simpleConnectionMaker.getConnection();
        Connection conn = connectionMaker.makeConnection();

        PreparedStatement ps = conn.prepareStatement("select * from USERS where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = new User();
        if (rs.next()) {
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        conn.close();

        return user;
    }
}
