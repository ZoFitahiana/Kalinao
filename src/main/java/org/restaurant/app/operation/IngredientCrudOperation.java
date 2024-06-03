package org.restaurant.app.operation;

import org.restaurant.app.entity.ConnectionDb;
import org.restaurant.app.entity.Ingredient;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
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
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ingredient> ingredients = new ArrayList<>();

        try {
            String sql = "SELECT * FROM ingredient";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_ingredient");
                String nom = resultSet.getString("nom");
                double prix = resultSet.getDouble("prix");
                int idUnite = resultSet.getInt("id_unite");
                double stock = resultSet.getDouble("stock");

                Ingredient ingredient = new Ingredient(id, nom, prix, idUnite, stock);
                ingredients.add(ingredient);
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
        return ingredients;
    }


    @Override
    public Ingredient save(Ingredient toSave) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "INSERT INTO ingredient (nom, prix, id_unite, stock) VALUES (?, ?, ?, ?)";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, toSave.getNom());
            statement.setDouble(2, toSave.getPrix());
            statement.setInt(3, toSave.getIdUnite());
            statement.setDouble(4, toSave.getStock());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insertion de l'ingrédient a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toSave.setIdIngredient(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La récupération de l'ID après l'insertion a échoué.");
                }
            }

            return toSave;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'ingrédient.", e);
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
        Connection connection = null ;
        PreparedStatement statement = null ;
        try {
            String sql = "DELETE FROM ingredient WHERE id_ingredient = ?";
            connection = ConnectionDb.createConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
