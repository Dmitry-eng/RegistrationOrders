package com.repository;

import com.model.Mechanic;

import java.util.List;

public interface MechanicDAO extends ObjectDAO<Mechanic> {
  List<Mechanic> findAllByFragments(String text);
}
