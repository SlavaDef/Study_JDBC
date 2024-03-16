package org.example.blog.dao;

import org.example.blog.entyti.User;
import org.example.robot.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.robot.Constants.*;

public class JdbcUserDaoImp implements UserDao {

    private final Connection connection;
    private final Statement st;

    public JdbcUserDaoImp() throws SQLException {
        connection = DriverManager.getConnection(MYSQL,"root",new Constants().getPAROLL());
        st = connection.createStatement();
    }


    @Override
    public void save(User user) {
        try (//Connection connection = DriverManager.getConnection(MYSQL, "root", new Constants().getPAROLL());
             PreparedStatement pr = connection.prepareStatement(INSERT_INTO_USERS)) {
            pr.setString(1, user.getFullName());
            pr.setString(2, user.getPseudonym());
            pr.setString(3, user.getEmail());
            pr.execute(); // виконання запиту
        } catch (SQLException e) {
            throw new IllegalStateException("Cant save user", e);
        }
    }

    @Override
    public List<User> getAll() {
        try {//(Connection connection = DriverManager.getConnection(MYSQL, "root", new Constants().getPAROLL());
            // Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users");
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                String fullName = rs.getString("fullName");
                String pseudonym = rs.getString("pseudonym");
                String email = rs.getString("email");
                users.add(new User(fullName, pseudonym, email));
            }

            return users;

        } catch (SQLException e) {
            throw new IllegalStateException("Cant get users", e);
        }
    }

    @Override
    public void createTable() {
        try //(Connection connection = DriverManager.getConnection(MYSQL, "root", new Constants().getPAROLL());
            // Statement st = connection.createStatement()) {
        {
            st.execute("DROP TABLE IF EXISTS users");
              st.execute(CREATE_USERS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}