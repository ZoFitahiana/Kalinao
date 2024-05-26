package org.restaurant.app.operation;

import org.restaurant.app.entity.ConnectionDb;
import org.restaurant.app.entity.Ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class IngredientCrudOperation implements CrudOperation<Ingredient>{
    @Override
    public Ingredient findById() {
        return null;
    }

    @Override
    public List<Ingredient> findAll() {
        return null;
    }

    @Override
    public Ingredient save(Ingredient toSave) {
        return null;
    }

    @Override
    public Ingredient Update(Ingredient toUpdate) {
        Connection connection = null ;
        PreparedStatement statement = null ;
        try{
            String sql = "UPDATE ingredient set nom = ? , prix = ? , id_unite = ? , stock = ? WHERE id_ingredient = ? ";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1,toUpdate.getNom());
            statement.setDouble(2,toUpdate.getPrix());
            statement.setInt(3,toUpdate.getIdUnite());
            statement.setDouble(4,toUpdate.getStock());
            statement.setInt(5,toUpdate.getIdIngredient());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (statement != null) statement.close();
                if (connection != null ) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return toUpdate;
    }

    @Override
    public void delete(int id) {

    }
}
