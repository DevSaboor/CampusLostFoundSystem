package dao;

import model.User;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User login(String email, String password) {

        String sql = """
                SELECT user_id, name, email, password, phone, role
                FROM users
                WHERE email = ? AND password = ?
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new User(
                    result.getInt("user_id"),
                    result.getString("name"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getString("phone"),
                    result.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}