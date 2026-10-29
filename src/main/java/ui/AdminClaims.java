package ui;

import dao.AdminClaimDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.util.List;

public class AdminClaims {

    public static void show(Stage stage, User user) {

        Label title = new Label("Pending Claims");

        title.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        TableView<String[]> table = new TableView<>();

        TableColumn<String[], String> claimId =
                new TableColumn<>("Claim ID");

        claimId.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue()[0]
                )
        );

        TableColumn<String[], String> itemId =
                new TableColumn<>("Item ID");

        itemId.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue()[1]
                )
        );

        TableColumn<String[], String> itemTitle =
                new TableColumn<>("Item");

        itemTitle.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue()[2]
                )
        );

        TableColumn<String[], String> userId =
                new TableColumn<>("User ID");

        userId.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue()[3]
                )
        );

        TableColumn<String[], String> userName =
                new TableColumn<>("User");

        userName.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue()[4]
                )
        );

        TableColumn<String[], String> description =
                new TableColumn<>("Claim Description");

        description.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue()[5]
                )
        );

        TableColumn<String[], String> status =
                new TableColumn<>("Status");

        status.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue()[6]
                )
        );

        table.getColumns().addAll(
                claimId,
                itemId,
                itemTitle,
                userId,
                userName,
                description,
                status
        );

        AdminClaimDAO dao =
                new AdminClaimDAO();

        loadClaims(table, dao);

        Button approveButton =
                new Button("Approve Selected");

        Button rejectButton =
                new Button("Reject Selected");

        Button refreshButton =
                new Button("Refresh");

        Button backButton =
                new Button("Back");

        approveButton.setOnAction(event -> {

            String[] selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {
                return;
            }

            int selectedClaimId =
        Integer.parseInt(selected[0]);

int selectedItemId =
        Integer.parseInt(selected[1]);

boolean success =
        dao.approveClaim(
                selectedClaimId,
                selectedItemId
        );

            if (success) {

                loadClaims(table, dao);

            } else {

                showMessage(
                        "Could not approve claim."
                );
            }
        });

        rejectButton.setOnAction(event -> {

            String[] selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {
                return;
            }

           int selectedClaimId =
        Integer.parseInt(selected[0]);

boolean success =
        dao.rejectClaim(selectedClaimId);

            if (success) {

                loadClaims(table, dao);

            } else {

                showMessage(
                        "Could not reject claim."
                );
            }
        });

        refreshButton.setOnAction(event ->
                loadClaims(table, dao)
        );

        backButton.setOnAction(event ->
                Dashboard.show(stage, user)
        );

        VBox root = new VBox(
                15,
                title,
                table,
                approveButton,
                rejectButton,
                refreshButton,
                backButton
        );

        root.setPadding(new Insets(25));

        Scene scene =
                new Scene(root, 1100, 650);

                Theme.apply(scene);

        stage.setTitle("Admin - Claims");
        stage.setScene(scene);
    }

    private static void loadClaims(
            TableView<String[]> table,
            AdminClaimDAO dao
    ) {

        List<String[]> claims =
                dao.getPendingClaims();

        table.setItems(
                FXCollections.observableArrayList(
                        claims
                )
        );
    }

    private static void showMessage(
            String message
    ) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}