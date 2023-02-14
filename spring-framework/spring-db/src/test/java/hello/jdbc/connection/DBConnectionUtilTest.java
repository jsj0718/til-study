package hello.jdbc.connection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class DBConnectionUtilTest {

    @Test
    void connNullTest() {
        //given
        Connection conn = DBConnectionUtil.getConnection();

        //when & then
        assertThat(conn).isNotNull();
    }
}