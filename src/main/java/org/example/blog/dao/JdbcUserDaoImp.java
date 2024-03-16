package org.example.blog.dao;

import org.example.blog.entyti.User;
import org.example.robot.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.robot.Constants.*;

public class JdbcUserDaoImp implements UserDao {

    private final Connection connection;
    private final Statement st;

    public JdbcUserDaoImp() throws SQLException {
        connection = DriverManager.getConnection(MYSQL, "root", new Constants().getPAROLL());
        st = connection.createStatement();
    }


    @Override
    public void save(User user) {
        try (PreparedStatement pr = connection.prepareStatement(INSERT_INTO_USERS)) {
            pr.setLong(1, user.getUser_id());
            pr.setString(2, user.getFullName());
            pr.setString(3, user.getPseudonym());
            pr.setString(4, user.getEmail());
            pr.execute(); // виконання запиту
        } catch (SQLException e) {
            throw new IllegalStateException("Cant save user", e);
        }
    }

    @Override
    public List<User> getAll() {
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                Long userId = rs.getLong("user_id");
                String fullName = rs.getString("fullName");
                String pseudonym = rs.getString("pseudonym");
                String email = rs.getString("email");
                users.add(new User(userId, fullName, pseudonym, email));
            }

            return users;

        } catch (SQLException e) {
            throw new IllegalStateException("Cant get users", e);
        }
    }

    @Override
    public void createTable() {
        try {
            st.execute("DROP TABLE IF EXISTS users");
            st.execute(CREATE_USERS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<User> getById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT user_id, fullName, pseudonym, email FROM users WHERE user_id = ?")) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String fullName = resultSet.getString("fullName");
                String pseudonym = resultSet.getString("pseudonym");
                String email = resultSet.getString("email");
                User user = new User(userId, fullName, pseudonym, email);
                return Optional.of(user); // обгортка
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Cant find user with this id", e);
        }
    }

    @Override
    public void deleteUser(User user) {

    }
}