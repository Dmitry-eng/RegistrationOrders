package com.repository;

import com.model.Client;

import java.util.List;

public interface ClientDAO extends ObjectDAO<Client> {
    List<Client> findAllByFragments(String text);
}
