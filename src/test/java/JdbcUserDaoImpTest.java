import org.example.blog.dao.JdbcUserDaoImp;
import org.example.blog.entyti.User;
import org.example.robot.Constants;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.example.robot.Constants.MYSQL;
import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserDaoImpTest {

    private final JdbcUserDaoImp daoImp;

    User user = new User(1L, "BobG", "djBOB", "Bob@gmail.com");

    public JdbcUserDaoImpTest() throws SQLException {
        daoImp = new JdbcUserDaoImp();
        daoImp.createTable();
    }

   // @AfterEach
    void cleanUpDb(){ // метод для очищення таблиці щоб не було конфліктів між тестами
        try(Connection connection = DriverManager.getConnection(MYSQL, "root", new Constants().getPAROLL());
            Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE users");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAll_shouldReturnSavedUsers()  {

        List<User> users = List.of(new User(1L,"userFullName","userPsevdo", "userAmail"),
                new User(2L,"userFullName2","userPsevdo2", "userAmail2"));

        for (User user : users) {
            daoImp.save(user);
        }

        List<User> actual = daoImp.getAll();
        assertEquals(users,actual);
    }

    @Test
    void getIdIfPresentInBd(){

        daoImp.save(user);
        Optional<User> actual = daoImp.getById(user.getUser_id()); //!
        assertTrue(actual.isPresent()); // !
        assertEquals(user,actual.get()); // !
    }

    @Test
    void getEmptyOptionalIfNoUserFound(){
        Optional<User> res = daoImp.getById(1L);
        assertFalse(res.isPresent()); // перевірка що такого нема

    }

    @Test
    void checkDeleteUser(){
        daoImp.save(user);
        daoImp.deleteEntyti(user);
        Optional<User> actual = daoImp.getById(user.getUser_id()); //!
        assertTrue(actual.isEmpty()); // !
      //  assertFalse(actual.isPresent());
    }

    @Test
    void updateUserTest(){ // TO-DO

        daoImp.save(user);
        daoImp.updateUser(1L,"BobG2", "djBOB2", "Bob2@gmail.com");
        Optional<User> actual = daoImp.getById(user.getUser_id());
        assertEquals(user.getUser_id(),1L);
    }

    @Test
    void updateUser2Test(){ // TO-DO
        daoImp.save(user);
        User user2 = new User(1L,"BobG2", "djBOB2", "Bob2@gmail.com");
        daoImp.updateEntyti(user2,1L);
        Optional<User> actual = daoImp.getById(user.getUser_id());
        assertEquals(user2,actual.get());
    }

}
