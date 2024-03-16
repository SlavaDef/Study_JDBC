import org.example.blog.dao.JdbcUserDaoImp;
import org.example.blog.entyti.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcUserDaoImpTest {



    @Test
    void getAll_shouldReturnSavedUsers() {
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
