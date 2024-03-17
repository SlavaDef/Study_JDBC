package org.example.blog.dao;

import org.example.blog.entyti.User;
import org.example.robot.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.robot.Constants.*;

public class JdbcUserDaoImp implements UserDao{

    private final Connection connection;
    private final Statement st;

    public JdbcUserDaoImp() throws SQLException {
        connection = DriverManager.getConnection(MYSQL, "root", new Constants().getPAROLL());
        st = connection.createStatement();
    }


    @Override
    public void save(User user) {
        try (PreparedStatement pr = connection.prepareStatement(INSERT_INTO_USERS)) {
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
        try {
            try (PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE user_id=?")) {
                st.setLong(1, user.getUser_id());
                st.executeUpdate();
            }

            System.out.println(" User was deleted");

        } catch (SQLException e) {
            throw new IllegalStateException("Cant delete user with this id", e);
        }
    }

    @Override
    public void updateUser(Long id, String fullName, String pseudonym, String email ) {
        try {
            try (PreparedStatement st = connection.prepareStatement(
                    "UPDATE users SET fullName=?,pseudonym=?,email=? WHERE user_id ="+id)) {
                st.setString(1, fullName );
                st.setString(2, pseudonym);
                st.setString(3, email);
                st.executeUpdate();

            }

            System.out.println(" User was updated");

        } catch (SQLException e) {
            throw new IllegalStateException("Cant delete user with this id", e);
        }
    }

    @Override
    public void updateUser2(User user, Long id) {
        try {
            try (PreparedStatement st = connection.prepareStatement(
                    "UPDATE users SET fullName=?,pseudonym=?,email=? WHERE user_id ="+id)) {
                st.setString(1, user.getFullName() );
                st.setString(2, user.getPseudonym());
                st.setString(3, user.getEmail());
                st.executeUpdate();

            }

            System.out.println(" User was updated");

        } catch (SQLException e) {
            throw new IllegalStateException("Cant delete user with this id", e);
        }
    }

    public static void main(String[] args) throws SQLException {
        JdbcUserDaoImp daoImp = new JdbcUserDaoImp();
        daoImp.createTable();
        User user = new User("a","b","c");
        daoImp.save(user);


       System.out.println(daoImp.getAll());

      // daoImp.updateUser(1L,"2","4","test");
        System.out.println(daoImp.getAll());
        List<User> users = daoImp.getAll();
       daoImp.updateUser2(new User("44","4444","444@"),1L);
        System.out.println(daoImp.getAll());
    }
}