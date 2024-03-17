import org.example.blog.dao.JdbcPostDaoImpl;
import org.example.blog.dao.JdbcUserDaoImp;
import org.example.blog.entyti.Post;
import org.example.blog.entyti.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcPostDaoImpTest {

    private final JdbcPostDaoImpl postDao;

    User user = new User(1L, "some name", "test", "c@");
    User user2 = new User(2L, "some name2", "test", "c@gmail");

    public JdbcPostDaoImpTest() throws SQLException {
        JdbcUserDaoImp  userDao = new JdbcUserDaoImp();
        postDao = new JdbcPostDaoImpl();
        userDao.createTable();
        postDao.createTable();
        userDao.save(user);
        userDao.save(user2);
    }

    @Test
    void getAll_shouldReturnSavedPosts() {
        LocalDateTime time = LocalDateTime.of(2024, 3, 17, 12, 5);
        LocalDateTime time2 = LocalDateTime.of(2024, 3, 17, 13, 15);

        List<Post> posts = List.of(new Post(1L, "some text", user, time),
                new Post(2L, "some text2", user2, time2));

        for (Post post : posts) {
            postDao.save(post);
        }

        List<Post> actual = postDao.getAll();
        assertEquals(posts, actual);
    }

    @Test
    void checkDeletePost() {
        LocalDateTime time = LocalDateTime.now();
        Post post = new Post(1L, "some text", user, time);
        postDao.save(post);
        postDao.deleteEntyti(post);
        Optional<Post> actual = postDao.getById(user.getUser_id()); //!
        assertTrue(actual.isEmpty()); // !
    }

    @Test
    void getEmptyOptionalIfNoUserFound() {
        Optional<Post> res = postDao.getById(1L);
        assertFalse(res.isPresent()); // перевірка що такого нема

    }
    @Test
    void getIdIfPresentInBd() {
        LocalDateTime time = LocalDateTime.of(2024, 3, 17, 12, 5);
        Post post = new Post(1L, "some text", user, time);
        postDao.save(post);
        Optional<Post> actual = postDao.getById(post.getPost_id()); //!
        assertTrue(actual.isPresent()); // !
        assertEquals(post, actual.get()); // !
    }

    @Test
    void updateUser2Test() {
        LocalDateTime time = LocalDateTime.of(2024, 3, 17, 12, 5);
        LocalDateTime time2 = LocalDateTime.of(2024, 3, 17, 15, 25);
        Post post = new Post(2L, "some text", user2, time);
        postDao.save(post);
        Post post2 = new Post(2L, "new text", user2, time2);
        postDao.updateEntyti(post2, 2L);

        Optional<Post> actual = postDao.getById(post2.getPost_id());
        assertEquals(post2, actual.get());
    }
}
