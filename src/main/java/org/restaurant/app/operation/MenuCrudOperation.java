package org.restaurant.app.operation;

import org.restaurant.app.entity.ConnectionDb;
import org.restaurant.app.entity.Menu;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository

public class MenuCrudOperation implements  CrudOperation<Menu>{
    @Override
    public Menu findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Menu menu = null;

        try {
            connection = ConnectionDb.createConnection();
            String sql = "SELECT * FROM menu WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                menu = new Menu();
                menu.setIdMenu(resultSet.getInt("id"));
                menu.setName(resultSet.getString("name"));
                menu.setPrixVente(resultSet.getDouble("prix_vente"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding menu by ID", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
        return menu;

    }

    @Override
    public List<Menu> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Menu> menus = new ArrayList<>();

        try {
            connection = ConnectionDb.createConnection();
            String sql = "SELECT * FROM menu";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Menu menu = new Menu();
                menu.setIdMenu(resultSet.getInt("id"));
                menu.setName(resultSet.getString("name"));
                menu.setPrixVente(resultSet.getDouble("prix_vente"));
                menus.add(menu);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all menus", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
        return menus;

    }

    @Override
    public Menu save(Menu toSave) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionDb.createConnection();
            String sql = "INSERT INTO menu (name, prix_vente) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, toSave.getName());
            statement.setDouble(2, toSave.getPrixVente());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving menu", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
        return toSave;
    }


    @Override
    public Menu Update(Menu toUpdate) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionDb.createConnection();
            String sql = "UPDATE menu SET name = ?, prix_vente = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, toUpdate.getName());
            statement.setDouble(2, toUpdate.getPrixVente());
            statement.setInt(3, toUpdate.getIdMenu());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating menu", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
        return toUpdate;

    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionDb.createConnection();
            String sql = "DELETE FROM menu WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting menu", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }
    }
