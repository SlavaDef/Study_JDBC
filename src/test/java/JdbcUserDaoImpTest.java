import org.example.blog.dao.JdbcUserDaoImp;
import org.example.blog.entyti.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcUserDaoImpTest {



    @Test
    void getAll_shouldReturnSavedUsers() throws SQLException {
        JdbcUserDaoImp daoImp = new JdbcUserDaoImp();
        daoImp.createTable();
        List<User> users = List.of(new User("userFullName","userPsevdo", "userAmail"),
                new User("userFullName2","userPsevdo2", "userAmail2"));

        for (User user : users) {
            daoImp.save(user);
        }

        List<User> actual = daoImp.getAll();
        assertEquals(users,actual);
    }

}
