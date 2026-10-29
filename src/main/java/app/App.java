package app;

import ui.Dashboard; 
import ui.Register; 
import ui.Theme; 
import dao.UserDAO; 
import model.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        showLogin(stage);
    }

    public static void showLogin(Stage stage) {

        Button registerButton =
        new Button("Create Account");

        registerButton.setOnAction(event ->
        Register.show(stage)
);

        Label title = new Label("Campus Lost & Found");
        title.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitle = new Label("Sign in to your account");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(250);

        Label message = new Label();

        loginButton.setOnAction(event -> {

            String email = emailField.getText().trim();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                message.setText("Please enter email and password.");
                return;
            }

            UserDAO userDAO = new UserDAO();
            User user = userDAO.login(email, password);

            if (user != null) {

                System.out.println("Login successful.");
                System.out.println("Role: " + user.getRole());

                Dashboard.show(stage, user);

            } else {

                message.setText("Invalid email or password.");
            }
        });

        VBox root = new VBox(
                15,
                title,
                subtitle,
                emailField,
                passwordField,
                loginButton,
                registerButton,
                message
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 600, 450);

        Theme.apply(scene);

        stage.setTitle("Campus Lost & Found");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}