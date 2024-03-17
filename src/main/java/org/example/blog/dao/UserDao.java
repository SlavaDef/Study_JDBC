package org.example.blog.dao;

import org.example.blog.entyti.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JenericDao<User>{

    void updateUser(Long id, String fullName, String pseudonym, String email );

}
