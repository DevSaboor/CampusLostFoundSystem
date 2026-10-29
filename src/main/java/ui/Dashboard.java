package ui;

import app.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class Dashboard {

    public static void show(Stage stage, User user) {

       Label title = new Label("Campus Lost & Found");
title.getStyleClass().add("title");
        title.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;"
        );

        Label welcome = new Label(
                "Welcome, " + user.getName() + "!"
        );

        Label role = new Label(
                "Role: " + user.getRole()
        );

        // Buttons
        Button reportLostButton = new Button("Report Lost Item");
        Button reportFoundButton = new Button("Report Found Item");
        Button searchButton = new Button("Search Items");
        Button myReportsButton = new Button("My Reports");
        Button adminClaimsButton = new Button("Manage Claims");
        Button logoutButton = new Button("Logout");

        searchButton.setOnAction(event ->
        SearchItems.show(stage, user)
);

myReportsButton.setOnAction(event ->
        MyReports.show(stage, user)
);

adminClaimsButton.setOnAction(event ->
        AdminClaims.show(stage, user)
);

adminClaimsButton.setVisible(
        "ADMIN".equals(user.getRole())
);

adminClaimsButton.setManaged(
        "ADMIN".equals(user.getRole())
);

        // Button sizes
        reportLostButton.setPrefWidth(220);
        reportFoundButton.setPrefWidth(220);
        searchButton.setPrefWidth(220);
        myReportsButton.setPrefWidth(220);
        logoutButton.setPrefWidth(220);

        reportLostButton.getStyleClass().add("primary-button");
reportFoundButton.getStyleClass().add("primary-button");

adminClaimsButton.getStyleClass().add("admin-button");

logoutButton.getStyleClass().add("logout-button");

        // Report Lost
        reportLostButton.setOnAction(event ->
                ReportItem.show(stage, user, "LOST")
        );

        // Report Found
        reportFoundButton.setOnAction(event ->
                ReportItem.show(stage, user, "FOUND")
        );

        // Logout
        logoutButton.setOnAction(event ->
                App.showLogin(stage)
        );

        VBox root = new VBox(
                15,
                title,
                welcome,
                role,
                reportLostButton,
                reportFoundButton,
                searchButton,
                myReportsButton,
                adminClaimsButton,
                logoutButton
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 650, 550);

       scene.getStylesheets().add(
        Dashboard.class.getResource("/style.css").toExternalForm()
);

        stage.setTitle("Campus Lost & Found - Dashboard");
        stage.setScene(scene);
    }
}