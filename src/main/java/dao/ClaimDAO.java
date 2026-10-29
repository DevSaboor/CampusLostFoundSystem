package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClaimDAO {

    public boolean submitClaim(
            int itemId,
            int userId,
            String description
    ) {

        String checkItemSQL = """
                SELECT user_id, status
                FROM items
                WHERE item_id = ?
                """;

        String checkClaimSQL = """
                SELECT claim_id
                FROM claims
                WHERE item_id = ?
                AND user_id = ?
                AND status = 'PENDING'
                """;

        String insertSQL = """
                INSERT INTO claims
                (item_id, user_id, claim_description)
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection()
        ) {

            // 1. Check the item
            try (
                    PreparedStatement statement =
                            connection.prepareStatement(checkItemSQL)
            ) {

                statement.setInt(1, itemId);

                ResultSet result =
                        statement.executeQuery();

                if (!result.next()) {
                    return false;
                }

                int ownerId =
                        result.getInt("user_id");

                String status =
                        result.getString("status");

                // Cannot claim your own item
                if (ownerId == userId) {
                    return false;
                }

                // Only ACTIVE items can be claimed
                if (!"ACTIVE".equals(status)) {
                    return false;
                }
            }

            // 2. Check duplicate pending claim
            try (
                    PreparedStatement statement =
                            connection.prepareStatement(checkClaimSQL)
            ) {

                statement.setInt(1, itemId);
                statement.setInt(2, userId);

                ResultSet result =
                        statement.executeQuery();

                if (result.next()) {
                    return false;
                }
            }

            // 3. Insert claim
            try (
                    PreparedStatement statement =
                            connection.prepareStatement(insertSQL)
            ) {

                statement.setInt(1, itemId);
                statement.setInt(2, userId);
                statement.setString(3, description);

                return statement.executeUpdate() == 1;
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}