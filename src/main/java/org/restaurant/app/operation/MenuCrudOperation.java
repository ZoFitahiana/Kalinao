package org.restaurant.app.operation;

import org.restaurant.app.entity.ConnectionDb;
import org.restaurant.app.entity.Menu;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository

public class MenuCrudOperation implements  CrudOperation<Menu>{
    @Override
    public Menu findById(int id) {
        return null;
    }

    @Override
    public List<Menu> findAll() {
        return null;
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
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
