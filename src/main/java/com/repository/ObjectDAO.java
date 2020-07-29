package com.repository;

import java.util.List;

public interface ObjectDAO<T> {
    void insert(T obj);

    List<T> findAll();

    void update(T obj);

    boolean deleteById(long id);

    T getById(long id);

}
