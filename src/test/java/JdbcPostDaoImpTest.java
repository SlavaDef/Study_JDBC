import org.example.blog.dao.JdbcPostDaoImpl;
import org.example.blog.dao.JdbcUserDaoImp;
import org.example.blog.entyti.Post;
import org.example.blog.entyti.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcPostDaoImpTest {

    private final JdbcPostDaoImpl postDao;
    private final JdbcUserDaoImp userDao;


    User user = new User(1L,"some name","test","c@");
    User user2 = new User(2L,"some name2","test","c@gmail");

    public JdbcPostDaoImpTest() throws SQLException {
        userDao = new JdbcUserDaoImp();
        postDao = new JdbcPostDaoImpl();
        userDao.createTable();
        postDao.createTable();
        userDao.save(user);
        userDao.save(user2);
    }

    @Test
    void getAll_shouldReturnSavedPosts()  {
        LocalDateTime time = LocalDateTime.of(2024, 3, 17, 12, 5);
        LocalDateTime time2 = LocalDateTime.of(2024, 3, 17, 13, 15);


        List<Post> posts = List.of(new Post(1L,"some text",user, time),
                new Post(2L,"some text2",user2, time2));

        for (Post post : posts) {
            postDao.save(post);
        }

        List<Post> actual = postDao.getAll();
        assertEquals(posts,actual);
    }
}
