package ui;

import app.App;
import dao.RegisterDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Register {

    public static void show(Stage stage) {

        Label title = new Label("Create Account");

        title.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        TextField nameField =
                new TextField();

        nameField.setPromptText("Full Name");

        TextField emailField =
                new TextField();

        emailField.setPromptText("Email");

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText("Password");

        TextField phoneField =
                new TextField();

        phoneField.setPromptText("Phone (optional)");

        Label message =
                new Label();

        Button registerButton =
                new Button("Register");

        Button backButton =
                new Button("Back to Login");

        registerButton.setOnAction(event -> {

            String name =
                    nameField.getText().trim();

            String email =
                    emailField.getText().trim();

            String password =
                    passwordField.getText();

            String phone =
                    phoneField.getText().trim();

            if (name.isEmpty()
                    || email.isEmpty()
                    || password.isEmpty()) {

                message.setText(
                        "Name, email and password are required."
                );

                return;
            }

            RegisterDAO dao =
                    new RegisterDAO();

            boolean success =
                    dao.registerUser(
                            name,
                            email,
                            password,
                            phone
                    );

            if (success) {

                message.setText(
                        "Account created successfully!"
                );

                nameField.clear();
                emailField.clear();
                passwordField.clear();
                phoneField.clear();

            } else {

                message.setText(
                        "Registration failed. Email may already exist."
                );
            }
        });

        backButton.setOnAction(event ->
                App.showLogin(stage)
        );

        VBox root = new VBox(
                15,
                title,
                nameField,
                emailField,
                passwordField,
                phoneField,
                registerButton,
                backButton,
                message
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        Scene scene =
                new Scene(root, 500, 600);

                Theme.apply(scene);

        stage.setTitle("Register");
        stage.setScene(scene);
    }
}