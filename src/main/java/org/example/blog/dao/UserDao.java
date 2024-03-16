package org.example.blog.dao;

import org.example.blog.entyti.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    void save(User user);

    List<User> getAll();

   void createTable();

   Optional<User> getById(Long id); // обгортка щоб не повертати нулі

    void deleteUser(User user);

    void updateUser(Long id, String fullName, String pseudonym, String email);

    void updateUser2(User user, Long id);

}
