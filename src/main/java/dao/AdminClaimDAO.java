package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminClaimDAO {

    public List<String[]> getPendingClaims() {

        List<String[]> claims = new ArrayList<>();

        String sql = """
                SELECT
                    c.claim_id,
                    c.item_id,
                    i.title,
                    c.user_id,
                    u.name,
                    c.claim_description,
                    c.status,
                    c.created_at
                FROM claims c
                JOIN items i
                    ON c.item_id = i.item_id
                JOIN users u
                    ON c.user_id = u.user_id
                WHERE c.status = 'PENDING'
                ORDER BY c.created_at DESC
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet result =
                        statement.executeQuery()
        ) {

            while (result.next()) {
                claims.add(new String[]{
                        String.valueOf(result.getInt("claim_id")),
                        String.valueOf(result.getInt("item_id")),
                        result.getString("title"),
                        String.valueOf(result.getInt("user_id")),
                        result.getString("name"),
                        result.getString("claim_description"),
                        result.getString("status"),
                        result.getString("created_at")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return claims;
    }

    public boolean approveClaim(int claimId, int itemId) {

        String claimSQL = """
                UPDATE claims
                SET status = 'APPROVED'
                WHERE claim_id = ?
                AND status = 'PENDING'
                """;

        String itemSQL = """
                UPDATE items
                SET status = 'CLAIMED'
                WHERE item_id = ?
                AND status = 'ACTIVE'
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection()
        ) {

            connection.setAutoCommit(false);

            try (
                    PreparedStatement claimStatement =
                            connection.prepareStatement(claimSQL);
                    PreparedStatement itemStatement =
                            connection.prepareStatement(itemSQL)
            ) {

                claimStatement.setInt(1, claimId);
                int claimUpdated =
                        claimStatement.executeUpdate();

                itemStatement.setInt(1, itemId);
                int itemUpdated =
                        itemStatement.executeUpdate();

                if (claimUpdated == 1 && itemUpdated == 1) {
                    connection.commit();
                    return true;
                }

                connection.rollback();
                return false;

            } catch (Exception e) {
                connection.rollback();
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectClaim(int claimId) {

        String sql = """
                UPDATE claims
                SET status = 'REJECTED'
                WHERE claim_id = ?
                AND status = 'PENDING'
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, claimId);

            return statement.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}