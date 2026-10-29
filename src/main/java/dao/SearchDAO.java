package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchDAO {

    public List<String[]> searchItems(
            String keyword,
            String type,
            String status
    ) {

        List<String[]> items = new ArrayList<>();

        String sql = """
                SELECT
                    i.item_id,
                    i.title,
                    c.category_name AS category,
                    i.description,
                    i.location,
                    i.item_date,
                    i.type,
                    i.status
                FROM items i
                JOIN categories c
                    ON i.category_id = c.category_id
                WHERE
                    (i.title LIKE ?
                    OR i.description LIKE ?
                    OR i.location LIKE ?)
                    AND (? = 'ALL' OR i.type = ?)
                    AND (? = 'ALL' OR i.status = ?)
                ORDER BY i.created_at DESC
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            String search = "%" + keyword + "%";

            statement.setString(1, search);
            statement.setString(2, search);
            statement.setString(3, search);

            statement.setString(4, type);
            statement.setString(5, type);

            statement.setString(6, status);
            statement.setString(7, status);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                items.add(new String[] {
                    String.valueOf(result.getInt("item_id")),
                    result.getString("title"),
                    result.getString("category"),
                    result.getString("description"),
                    result.getString("location"),
                    result.getString("item_date"),
                    result.getString("type"),
                    result.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}