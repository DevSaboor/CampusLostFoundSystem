package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterDAO {

    public boolean registerUser(
            String name,
            String email,
            String password,
            String phone
    ) {

        String sql = """
                INSERT INTO users
                (name, email, password, phone)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, phone);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}