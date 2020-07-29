package com.repository;


import com.model.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAOImpl implements StatusDAO {
    @Override
    public Status getById(long id) {
        String sql = "SELECT * FROM Status WHERE id=" + id;
        PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Status(
                        resultSet.getLong("id"),
                        resultSet.getString("description")
                );
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Status> findAll() {
        List<Status> orderList = new ArrayList<>();
        String sql = "SELECT * FROM Status";
        PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderList.add(
                        new Status(
                                resultSet.getLong("id"),
                                resultSet.getString("description")
                        )
                );
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

}
