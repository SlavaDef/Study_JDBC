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
                User user = new User(author,fullName,pseudonym,email);
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
        return Optional.empty();
    }

    @Override
    public void deleteUser(Post entity) {

    }

    @Override
    public void updateUser2(Post entity, Long id) {

    }

    public static void main(String[] args) throws SQLException {
        JdbcUserDaoImp imp1 = new JdbcUserDaoImp();
        JdbcPostDaoImpl imp = new JdbcPostDaoImpl();
        imp1.createTable();
        imp.createTable();
        User user = new User(1L,"Bob_JD", "BOB_2", "Bob@gmail.com");
        imp1.save(user);
        imp.save(new Post(1L,"Sunny", user,LocalDateTime.of(2024, 3, 17, 12, 5)));
        System.out.println( imp.getAll());
       // [Post{post_id=1, text='Sunny', author=User{user_id=1, fullName='Bob_JD', pseudonym='BOB_2', email='Bob@gmail.com'}, createdIn=2024-03-17T12:05}]
    }
}
