package org.restaurant.app.operation;

import org.restaurant.app.entity.ConnectionDb;
import org.restaurant.app.entity.StatusRestaurant;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantOperation {
    public List<StatusRestaurant> getStatusRestaurant(LocalDateTime start, LocalDateTime end) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<StatusRestaurant> statusList = new ArrayList<>();

        try {
            connection = ConnectionDb.createConnection();
            String sql = "SELECT " +
                    "r.id_restaurant AS ID, " +
                    "r.location AS \"Lieu du restaurant\", " +
                    "SUM(CASE WHEN m.name = 'Crêpe' THEN im.quantite_necessaire ELSE 0 END) AS \"Nombre Crêpes Vendus\", " +
                    "SUM(CASE WHEN m.name = 'Gaufre' THEN im.quantite_necessaire ELSE 0 END) AS \"Nombre Gaufres Vendus\", " +
                    "SUM(CASE WHEN m.name = 'Croissant' THEN im.quantite_necessaire ELSE 0 END) AS \"Nombre Croissants Vendus\", " +
                    "SUM(CASE WHEN m.name = 'Crêpe' THEN im.quantite_necessaire * m.prix_vente ELSE 0 END) AS \"Montant Crêpes Vendus\", " +
                    "SUM(CASE WHEN m.name = 'Gaufre' THEN im.quantite_necessaire * m.prix_vente ELSE 0 END) AS \"Montant Gaufres Vendus\", " +
                    "SUM(CASE WHEN m.name = 'Croissant' THEN im.quantite_necessaire * m.prix_vente ELSE 0 END) AS \"Montant Croissants Vendus\" " +
                    "FROM " +
                    "Restaurant r " +
                    "LEFT JOIN " +
                    "IngredientMenu im ON im.id_ingredient_menu = r.id_ingredient_menu " +
                    "LEFT JOIN " +
                    "Menu m ON im.id_menu = m.id_menu " +
                    "WHERE " +
                    "im.date_movement BETWEEN ? AND ? " +
                    "GROUP BY " +
                    "r.id_restaurant, r.location";

            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String location = resultSet.getString("Lieu du restaurant");
                int nbCrepesVendus = resultSet.getInt("Nombre Crêpes Vendus");
                int nbGaufresVendus = resultSet.getInt("Nombre Gaufres Vendus");
                int nbCroissantsVendus = resultSet.getInt("Nombre Croissants Vendus");
                double montantCrepesVendus = resultSet.getDouble("Montant Crêpes Vendus");
                double montantGaufresVendus = resultSet.getDouble("Montant Gaufres Vendus");
                double montantCroissantsVendus = resultSet.getDouble("Montant Croissants Vendus");

                StatusRestaurant status = new StatusRestaurant(id, location, nbCrepesVendus, nbGaufresVendus, nbCroissantsVendus, montantCrepesVendus, montantGaufresVendus, montantCroissantsVendus);
                statusList.add(status);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting status of restaurants", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }

        return statusList;
    }

}
