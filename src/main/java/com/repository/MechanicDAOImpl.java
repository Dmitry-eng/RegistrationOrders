package com.repository;


import com.model.Mechanic;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicDAOImpl implements MechanicDAO {

    @Override
    public void insert(Mechanic obj) {
        String sql = "INSERT INTO Mechanic(name,surname,middleName,hourPrice) VALUES (?,?,?,?)";
        sqlCommand(obj, sql);
    }

    @Override
    public List<Mechanic> findAll() {
        String sql = "SELECT * FROM Mechanic;";
        PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
        return resultSet(preparedStatement);
    }

    @Override
    public void update(Mechanic obj) {
        String sql = "UPDATE Mechanic Set name = ?  , surname = ? ,middleName = ? , hourPrice = ? WHERE id=" + obj.getId() + ";";
        sqlCommand(obj, sql);
    }

    @Override
    public boolean deleteById(long id) {
        try {
            String sql = "DELETE FROM Mechanic WHERE id=" + id;
            PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
            int result = preparedStatement.executeUpdate();
            return result!=0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Mechanic getById(long id) {
        String sql = "SELECT * FROM Mechanic WHERE id=" + id;
        PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
        return resultSet(preparedStatement).get(0);
    }

    private void sqlCommand(Mechanic obj, String sql) {
        try {
            PreparedStatement preparedStatement = BaseConnect.preparedStatement(sql);
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getSurname());
            preparedStatement.setString(3, obj.getMiddleName());
            preparedStatement.setDouble(4, obj.getHourPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Mechanic> findAllByFragments(String text) {

        PreparedStatement preparedStatement;
        String sql;
        try {
            Long id = Long.parseLong(text);
            sql = "SELECT * FROM Mechanic WHERE id= ? OR hourPrice= ? or name like ? OR surname like ? OR middleName like ?";
            preparedStatement = BaseConnect.preparedStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, id);
            preparedStatement.setString(3, "%" + text + "%");
            preparedStatement.setString(4, "%" + text + "%");
            preparedStatement.setString(5, "%" + text + "%");
        } catch (NumberFormatException e) {
            sql = "SELECT * FROM Mechanic WHERE name like ? OR surname like ? OR middleName like ?";
            preparedStatement = BaseConnect.preparedStatement(sql);
            try {
                preparedStatement.setString(1, "%" + text + "%");
                preparedStatement.setString(2, "%" + text + "%");
                preparedStatement.setString(3, "%" + text + "%");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultSet(preparedStatement);

    }

    private List<Mechanic> resultSet(PreparedStatement preparedStatement) {
        List<Mechanic> mechanics = new ArrayList<>();
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mechanics.add(
                        new Mechanic(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("surname"),
                                resultSet.getString("middleName"),
                                resultSet.getInt("hourPrice")
                        )
                );
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mechanics;
    }
}

