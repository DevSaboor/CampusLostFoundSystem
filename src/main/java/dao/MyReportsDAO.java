package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MyReportsDAO {

    public List<String[]> getMyReports(int userId) {

        List<String[]> reports = new ArrayList<>();

        String sql = """
                SELECT
                    i.item_id,
                    i.title,
                    c.category_name,
                    i.location,
                    i.item_date,
                    i.type,
                    i.status
                FROM items i
                JOIN categories c
                    ON i.category_id = c.category_id
                WHERE i.user_id = ?
                ORDER BY i.created_at DESC
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                reports.add(new String[] {
                    String.valueOf(result.getInt("item_id")),
                    result.getString("title"),
                    result.getString("category_name"),
                    result.getString("location"),
                    result.getString("item_date"),
                    result.getString("type"),
                    result.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
}