package org.example.blog.dao;

import org.example.blog.entyti.Post;
import org.example.blog.entyti.User;
import org.example.robot.Constants;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.robot.Constants.*;

public class JdbcPostDaoImpl implements PostDao {

    private final Connection connection;
    private final Statement st;

    public JdbcPostDaoImpl() throws SQLException {
        connection = DriverManager.getConnection(MYSQL, "root", new Constants().getPAROLL());
        st = connection.createStatement();
    }

    @Override
    public void save(Post post) {

        try (PreparedStatement pr = connection.prepareStatement(INSERT_INTO_POST)) {
            pr.setLong(1, post.getPost_id());
            pr.setString(2, post.getText());
            pr.setLong(3, post.getAuthor().getUser_id());
            pr.setObject(4, post.getCreatedIn());
            pr.execute(); // виконання запиту
        } catch (SQLException e) {
            throw new IllegalStateException("Cant save post", e);
        }

    }

    @Override
    public List<Post> getAll() {
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM post JOIN users ON users.user_id = post.author");
            List<Post> posts = new ArrayList<>();
            while (rs.next()) {
                Long postId = rs.getLong("post_id");
                String text = rs.getString("text");
                Long author = rs.getLong("author");
                String fullName = rs.getString("fullName");
                String pseudonym = rs.getString("pseudonym");
                String email = rs.getString("email");
                LocalDateTime createdIn = rs.getObject("createdIn", LocalDateTime.class); // явно задаємо обьект якого класу хочемо отримати
                User user = new User(author, fullName, pseudonym, email);
                posts.add(new Post(postId, text, user, createdIn));
            }

            return posts;

        } catch (SQLException e) {
            throw new IllegalStateException("Cant get posts", e);
        }
    }

    @Override
    public void createTable() {
        try {
            st.execute("DROP TABLE IF EXISTS post");
            st.execute(CREATE_POST);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Post> getById(Long id) {

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT post_id, text, author, createdIn, users.fullName, users.pseudonym,users.email FROM post JOIN users ON users.user_id = post.author WHERE post_id = ?")) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {

                Long postId = resultSet.getLong("post_id");
                String text = resultSet.getString("text");
                Long author = resultSet.getLong("author");
                String fullName = resultSet.getString("fullName");
                String pseudonym = resultSet.getString("pseudonym");
                String email = resultSet.getString("email");
                LocalDateTime dateTime = resultSet.getObject("createdIn", LocalDateTime.class);
                User user = new User(author, fullName, pseudonym, email);
                Post post = new Post(postId, text, user, dateTime);
                return Optional.of(post); // обгортка
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Cant find user with this id", e);
        }
    }

    @Override
    public void deleteEntyti(Post post) {
        try {
            try (PreparedStatement st = connection.prepareStatement("DELETE FROM post WHERE post_id=?")) {
                st.setLong(1, post.getPost_id());
                st.executeUpdate();
            }

            System.out.println(" Post was deleted");

        } catch (SQLException e) {
            throw new IllegalStateException("Cant delete post with this id", e);
        }
    }

    @Override
    public void updateEntyti(Post entity, Long id) {

        try {
            try (PreparedStatement st = connection.prepareStatement(
                    "UPDATE post SET text=?,createdIn=? WHERE post_id ="+id)) {
                st.setString(1, entity.getText());
                st.setObject(2, entity.getCreatedIn());
                st.executeUpdate();

            }

            System.out.println(" Post was updated");

        } catch (SQLException e) {
            throw new IllegalStateException("Cant update post with this id", e);
        }

    }

    public static void main(String[] args) throws SQLException {
        JdbcUserDaoImp userDao = new JdbcUserDaoImp();
        JdbcPostDaoImpl postDao = new JdbcPostDaoImpl();
        userDao.createTable();
        postDao.createTable();
        User user = new User(1L, "Bob_JD", "BOB_2", "Bob@gmail.com");
        userDao.save(user);
        Post post1 = new Post(1L, "Sunny", user, LocalDateTime.of(2024, 3, 17, 12, 5));
        postDao.save(post1);
        System.out.println(postDao.getAll());
        // [Post{post_id=1, text='Sunny', author=User{user_id=1, fullName='Bob_JD', pseudonym='BOB_2', email='Bob@gmail.com'}, createdIn=2024-03-17T12:05}]
        postDao.deleteEntyti(post1);
        System.out.println(postDao.getAll());
        Post post2 = new Post(1L, "Frosty", user, LocalDateTime.of(2024, 3, 17, 12, 5));
        postDao.save(post2);
        System.out.println("GetALL = " + postDao.getAll());
        System.out.println("Get = " + postDao.getById(1L).get());
        postDao.updateEntyti(new Post("some new text", LocalDateTime.now()),1L);
        System.out.println("Get = " + postDao.getById(1L).get());
    }
}
