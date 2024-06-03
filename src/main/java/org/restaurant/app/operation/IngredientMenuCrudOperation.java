package org.restaurant.app.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.restaurant.app.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public static double calculateStock(IngredientMenu menu) {
        double stock = 0.0;
        if (menu.getType().equals("entrer")) {
            stock += menu.getQuantiteNecessaire();
        }
        if (menu.getType().equals("sortie")) {
            stock -= menu.getQuantiteNecessaire();
        }
        return stock;
    }

    public Ingredient updateStock(IngredientMenu menu) {
        double stock = calculateStock(menu);
        Ingredient ingredient = ingredientCrudOperation.findById(menu.getIdIngredient());
        double newStock = ingredient.getStock() + stock;
        ingredient.setStock(newStock);
        return ingredientCrudOperation.Update(ingredient);
    }

    public List<StockMovement> getStockMovements(LocalDateTime start, LocalDateTime end) {
        List<StockMovement> movements = new ArrayList<>();
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
                double quantity = resultSet.getDouble("quantite_necessaire");
                String unitName = resultSet.getString("name");

                StockMovement movement = new StockMovement(dateMovement, ingredientName, type, quantity, unitName);
                movements.add(movement);
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
        return movements;
    }

    public List<IngredientUsage> getIngredientUsage(LocalDateTime start, LocalDateTime end) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            List<IngredientUsage> usageList = new ArrayList<>();

            String sql = "SELECT " +
                    "i.id_ingredient AS id_ingredient, " +
                    "i.nom AS nom_ingredient, " +
                    "m.name AS menu_plus_utilise, " +
                    "im.quantite_necessaire AS quantite_depensee, " +
                    "u.name AS unite " +
                    "FROM IngredientMenu im " +
                    "INNER JOIN Ingredient i ON i.id_ingredient = im.id_ingredient " +
                    "INNER JOIN Menu m ON m.id_menu = im.id_menu " +
                    "INNER JOIN Unite u ON u.id_unite = im.id_unite " +
                    "WHERE im.date_movement BETWEEN ? AND ? " +
                    "GROUP BY i.id_ingredient, i.nom, m.name, im.quantite_necessaire, u.name " +
                    "ORDER BY im.quantite_necessaire DESC " +
                    "LIMIT 3";

            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idIngredient = resultSet.getInt("id_ingredient");
                String nomIngredient = resultSet.getString("nom_ingredient");
                String menuPlusUtilise = resultSet.getString("menu_plus_utilise");
                double quantiteDepensee = resultSet.getDouble("quantite_depensee");
                String unite = resultSet.getString("unite");

                IngredientUsage usage = new IngredientUsage(idIngredient, nomIngredient, menuPlusUtilise, quantiteDepensee, unite);
                usageList.add(usage);
            }
            return usageList;
        } catch (Exception e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }


}
