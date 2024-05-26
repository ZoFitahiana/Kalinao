package org.restaurant.app.operation;

import org.restaurant.app.entity.ConnectionDb;
import org.restaurant.app.entity.IngredientMenu;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
@Repository

public class IngredientMenuCrudOperation implements CrudOperation<IngredientMenu> {
    @Override
    public IngredientMenu findById() {

        return null;
    }

    @Override
    public List<IngredientMenu> findAll() {
        return null;
    }

    @Override
    public IngredientMenu save(IngredientMenu toSave) {
        Connection connection = null;
        PreparedStatement statement = null ;
        try{
         String sql = "INSERT INTO ingredient_menu (id_ingredient_menu ,id_menu,id_ingredient,quantite_necessaire,type,id_unite,date_movement) VALUES (?,?,?,?,?,?,?)";
         connection = ConnectionDb.createConnection();
         statement = connection.prepareStatement(sql);
         statement.setInt(1,toSave.getIdIngredientMenu());
         statement.setInt(2,toSave.getIdMenu());
         statement.setInt(3,toSave.getIdIngredient());
         statement.setDouble(5,toSave.getQuantiteNecessaire());
         statement.setInt(6,toSave.getIdUnite());
         statement.setObject(7,toSave.getDateMovement());
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
        return toSave;
    }

    @Override
    public IngredientMenu Update(IngredientMenu toUpdate) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
