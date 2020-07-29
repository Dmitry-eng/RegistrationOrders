package com.repository;

import com.model.Client;
import com.model.Mechanic;
import com.model.Order;
import com.model.Status;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private ObjectDAO<Client> clientObject = new ClientDAOImpl();
    private ObjectDAO<Mechanic> mechanicObjectDAO = new MechanicDAOImpl();
    private StatusDAO statusDao = new StatusDAOImpl();

    @Override
    public void insert(Order obj) {
        String sql = "INSERT INTO Orders (description,  client,  mechanic, creature,  completion,  price,  status) VALUES (?,?,?,?,?,?,?);";
        sqlCommand(obj, sql);
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM Orders";
        PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
        return resultSet(preparedStatement);
    }

    @Override
    public void update(Order obj) {
        String sql = "UPDATE Orders Set description = ?  , client = ? , mechanic = ? , creature = ? , completion = ? , price = ? , status = ?  WHERE id=" + obj.getId() + ";";
        sqlCommand(obj, sql);
    }

    @Override
    public boolean deleteById(long id) {
        try {
            String sql = "DELETE FROM Orders WHERE id=" + id;
            PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
            int result = preparedStatement.executeUpdate();
            return result!=0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Order getById(long id) {

        String sql = "SELECT * FROM Orders WHERE id=" + id;
        PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
        return resultSet(preparedStatement).get(0);
    }

    private void sqlCommand(Order obj, String sql) {
        try {
            PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
            preparedStatement.setString(1, obj.getDescription());
            preparedStatement.setLong(2, obj.getClient().getId());
            preparedStatement.setLong(3, obj.getMechanic().getId());
            preparedStatement.setTimestamp(4, obj.getCreature());
            preparedStatement.setTimestamp(5, obj.getCompletion());
            preparedStatement.setInt(6, obj.getPrice());
            preparedStatement.setLong(7, obj.getStatus().getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> findAllByClientOrStatusOrDescription(Client client, Status status, String description) {
        StringBuilder builder = new StringBuilder("SELECT * FROM Orders ");
        PreparedStatement preparedStatement = null;
        try {
            if (client != null) builder.append("client = ? and ");
            if (status != null) builder.append(" status = ? and ");
            if (description != null) builder.append(" description like ? ");
            ;
            if (builder.toString().substring(builder.length() - 3, builder.length()).equals("and"))
                builder = builder.delete(builder.length() - 3, builder.length());
            if (builder.length() > 27) builder.insert(21, " where ");
            int count = 1;
            preparedStatement = BaseConnect.preparedStatement(builder.toString());
            if (client != null) {
                preparedStatement.setLong(count, client.getId());
                count++;
            }
            if (status != null) {
                preparedStatement.setLong(count, status.getId());
                count++;
            }
            if (description != null) preparedStatement.setString(count, "%" + description + "%");
        } catch (IndexOutOfBoundsException | NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        return resultSet(preparedStatement);
    }


    @Override
    public List<Order> findAllByMechanicAndStatus(Mechanic mechanic, Status status) {
        String sql = "SELECT * FROM Orders WHERE mechanic = ? and status = ?";
        PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
        try {
            preparedStatement.setLong(1, mechanic.getId());
            preparedStatement.setLong(2, status.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet(preparedStatement);
    }

    @Override
    public int getCountByMechanic(Mechanic mechanic) {
        int count = 0;
        String sql = "SELECT COUNT(*) as count from Orders where mechanic = ?";
        PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
        try {
            preparedStatement.setLong(1, mechanic.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getSumByMechanic(Mechanic mechanic) {
        int sum = 0;
        String sql = "SELECT sum(price) AS result from Orders where mechanic = ?";
        PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
        try {
            preparedStatement.setLong(1, mechanic.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sum = resultSet.getInt("result");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    private List<Order> resultSet(PreparedStatement preparedStatement) {
        List<Order> orderList = new ArrayList<>();
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderList.add(
                        new Order(
                                resultSet.getLong("id"),
                                resultSet.getString("description"),
                                clientObject.getById(resultSet.getLong("client")),
                                mechanicObjectDAO.getById(resultSet.getLong("mechanic")),
                                resultSet.getTimestamp("creature"),
                                resultSet.getTimestamp("completion"),
                                resultSet.getInt("price"),
                                statusDao.getById(resultSet.getLong("status"))
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
