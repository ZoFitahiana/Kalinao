package org.restaurant.app.operation;

import org.restaurant.app.entity.ConnectionDb;
import org.restaurant.app.entity.Ingredient;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository

public class IngredientCrudOperation implements CrudOperation<Ingredient>{
    @Override
    public Ingredient findById(int id) {
        Connection connection = null ;
        PreparedStatement statement = null ;
        ResultSet resultSet = null ;
        Ingredient ingredient = null ;
        try{
         String sql = "SELECT * FROM ingredient WHERE id_ingredient = ?";
         connection = ConnectionDb.createConnection();
         statement = connection.prepareStatement(sql);
         statement.setInt(1,id);
         resultSet = statement.executeQuery();
         while (resultSet.next()){
            int id_ingredient = resultSet.getInt("id_ingredient");
            String nom = resultSet.getString("nom");
            double prix = resultSet.getDouble("prix");
            int id_unite = resultSet.getInt("id_unite");
            double stock = resultSet.getDouble("stock");
            ingredient = new Ingredient(id_ingredient,nom,prix,id_unite,stock);
         }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredient;
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
