package org.example.blog.dao;

import org.example.blog.entyti.Post;
import org.example.robot.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.example.robot.Constants.MYSQL;

public class JdbcPostDaoImpl implements PostDao {

    private final Connection connection;
    private final Statement st;

    public JdbcPostDaoImpl() throws SQLException {
        connection = DriverManager.getConnection(MYSQL, "root", new Constants().getPAROLL());
        st = connection.createStatement();
    }
    @Override
    public void save(Post entity) {

    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public void createTable() {

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
}
