package org.example.blog.dao;

import org.example.blog.entyti.User;

import java.util.List;
import java.util.Optional;

public interface JenericDao <T> {
    void save(T entity);

    List<T> getAll();

    void createTable();

    Optional<T> getById(Long id); // обгортка щоб не повертати нулі

    void deleteEntyti(T entity);

    void updateEntyti(T entity, Long id);
}
