package com.repository;

import com.model.Client;
import com.model.Mechanic;
import com.model.Order;
import com.model.Status;

import java.util.List;

public interface OrderDAO extends ObjectDAO<Order> {
    List<Order> findAllByMechanicAndStatus(Mechanic mechanic, Status status);

    int getCountByMechanic(Mechanic mechanic);

    int getSumByMechanic(Mechanic mechanic);

    List<Order> findAllByClientOrStatusOrDescription(Client client, Status status, String description);
}
