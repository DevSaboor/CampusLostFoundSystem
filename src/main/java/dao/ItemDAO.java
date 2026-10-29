package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ItemDAO {

    public boolean addItem(
            int userId,
            int categoryId,
            String title,
            String description,
            String location,
            String itemDate,
            String type
    ) {

        String sql = """
                INSERT INTO items
                (user_id, category_id, title, description, location, item_date, type)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);
            statement.setInt(2, categoryId);
            statement.setString(3, title);
            statement.setString(4, description);
            statement.setString(5, location);
            statement.setString(6, itemDate);
            statement.setString(7, type);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}