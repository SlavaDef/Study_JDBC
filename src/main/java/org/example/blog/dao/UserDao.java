package org.example.blog.dao;

import org.example.blog.entyti.User;

import java.util.List;

public interface UserDao {

    void save(User user);

    List<User> getAll();

   void createTable();

}
