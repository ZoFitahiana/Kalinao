package org.restaurant.app.operation;

import org.restaurant.app.entity.ConnectionDb;
import org.restaurant.app.entity.Ingredient;
import org.restaurant.app.entity.IngredientMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;


@Repository

public class IngredientMenuCrudOperation implements CrudOperation<IngredientMenu> {
    private  IngredientCrudOperation ingredientCrudOperation;
    @Autowired
    public IngredientMenuCrudOperation(IngredientCrudOperation ingredientCrudOperation) {
        this.ingredientCrudOperation = ingredientCrudOperation;
    }

    @Override
    public IngredientMenu findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        IngredientMenu ingredientMenu = null;

        try {
            String sql = "SELECT * FROM IngredientMenu WHERE id_ingredient_menu = ?";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ingredientMenu = new IngredientMenu();
                ingredientMenu.setIdIngredientMenu(resultSet.getInt("id_ingredient_menu"));
                ingredientMenu.setIdMenu(resultSet.getInt("id_menu"));
                ingredientMenu.setIdIngredient(resultSet.getInt("id_ingredient"));
                ingredientMenu.setQuantiteNecessaire(resultSet.getDouble("quantite_necessaire"));
                ingredientMenu.setType(resultSet.getString("type"));
                ingredientMenu.setIdUnite(resultSet.getInt("id_unite"));
                ingredientMenu.setDateMovement(resultSet.getTimestamp("date_movement").toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return ingredientMenu;
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
         String sql = "INSERT INTO IngredientMenu  (id_ingredient_menu,id_menu,id_ingredient,quantite_necessaire,type,id_unite,date_movement) VALUES (?,?,?,?,?,?,?)";
         connection = ConnectionDb.createConnection();
         statement = connection.prepareStatement(sql);
         statement.setInt(1,toSave.getIdIngredientMenu());
         statement.setInt(2,toSave.getIdMenu());
         statement.setInt(3,toSave.getIdIngredient());
         statement.setDouble(4,toSave.getQuantiteNecessaire());
         statement.setInt(5,toSave.getIdUnite());
         statement.setInt(6,toSave.getIdUnite());
         statement.setObject(7,toSave.getDateMovement());
         statement.executeUpdate();
         updateStock(toSave);
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

    public static double stockIngredient(IngredientMenu menu){
        double stock = 0.0;
        if (menu.getType().equals("entrer")){
            stock += menu.getQuantiteNecessaire();
        }
        if (menu.getType().equals("sortie")){
            stock -= menu.getQuantiteNecessaire();
        }
        return stock;
    }
    public Ingredient updateStock(IngredientMenu menu){
        double stock = stockIngredient(menu);
        Ingredient ingredient = ingredientCrudOperation.findById(menu.getIdIngredient());
        Ingredient ingredientUpdateStock = new Ingredient(ingredient.getIdIngredient(), ingredient.getNom(),ingredient.getPrix(),ingredient.getIdUnite(),stock);
        ingredientCrudOperation.Update(ingredientUpdateStock);
        return  ingredientUpdateStock;
    }

    public void consultationMouvementStock(LocalDateTime start, LocalDateTime end) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT im.date_movement, i.nom, im.type, im.quantite_necessaire, u.name " +
                    "FROM IngredientMenu im " +
                    "INNER JOIN Ingredient i ON i.id_ingredient = im.id_ingredient " +
                    "INNER JOIN Unite u ON u.id_unite = im.id_unite " +
                    "WHERE im.date_movement BETWEEN ? AND ?";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LocalDateTime dateMovement = resultSet.getTimestamp("date_movement").toLocalDateTime();
                String ingredientName = resultSet.getString("nom");
                String type = resultSet.getString("type");
                double quantiteNecessaire = resultSet.getDouble("quantite_necessaire");
                String uniteName = resultSet.getString("name");

                System.out.println("Date de mouvement : " + dateMovement);
                System.out.println("Ingrédient : " + ingredientName);
                System.out.println("Type : " + type);
                System.out.println("Quantité nécessaire : " + quantiteNecessaire);
                System.out.println("Unité : " + uniteName);
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la consultation des mouvements de stock.", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la fermeture des ressources de base de données.", e);
            }
        }
    }


}
