package com.repository;

import com.model.Status;

import java.util.List;

public interface StatusDAO {
    Status getById(long id);
    List<Status> findAll();
}