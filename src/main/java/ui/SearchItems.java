package ui;

import dao.SearchDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.util.List;

public class SearchItems {

    public static void show(Stage stage, User user) {

        Label title = new Label("Search Lost & Found Items");

        title.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        TextField searchField = new TextField();
        searchField.setPromptText(
                "Search by title, description or location"
        );

        ComboBox<String> typeBox = new ComboBox<>();

        typeBox.setItems(FXCollections.observableArrayList(
                "ALL",
                "LOST",
                "FOUND"
        ));

        typeBox.setValue("ALL");

        ComboBox<String> statusBox = new ComboBox<>();

        statusBox.setItems(FXCollections.observableArrayList(
                "ALL",
                "ACTIVE",
                "CLAIMED",
                "RETURNED",
                "CLOSED"
        ));

        statusBox.setValue("ALL");

        Button searchButton = new Button("Search");

        TableView<String[]> table = new TableView<>();

        TableColumn<String[], String> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue()[0]
                )
        );

        TableColumn<String[], String> titleColumn =
                new TableColumn<>("Title");

        titleColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue()[1]
                )
        );

        TableColumn<String[], String> categoryColumn =
                new TableColumn<>("Category");

        categoryColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue()[2]
                )
        );

        TableColumn<String[], String> locationColumn =
                new TableColumn<>("Location");

        locationColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue()[4]
                )
        );

        TableColumn<String[], String> dateColumn =
                new TableColumn<>("Date");

        dateColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue()[5]
                )
        );

        TableColumn<String[], String> typeColumn =
                new TableColumn<>("Type");

        typeColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue()[6]
                )
        );

        TableColumn<String[], String> statusColumn =
                new TableColumn<>("Status");

        statusColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue()[7]
                )
        );

        table.getColumns().addAll(
                idColumn,
                titleColumn,
                categoryColumn,
                locationColumn,
                dateColumn,
                typeColumn,
                statusColumn
        );

        searchButton.setOnAction(event -> {

            SearchDAO searchDAO = new SearchDAO();

            List<String[]> results = searchDAO.searchItems(
                    searchField.getText().trim(),
                    typeBox.getValue(),
                    statusBox.getValue()
            );

            table.setItems(
                    FXCollections.observableArrayList(results)
            );
        });

        Button claimButton = new Button("Claim Selected Item");

claimButton.setOnAction(event -> {

    String[] selected =
            table.getSelectionModel().getSelectedItem();

    if (selected == null) {
        return;
    }

    int itemId =
            Integer.parseInt(selected[0]);

    String itemTitle =
            selected[1];

    SubmitClaim.show(
            stage,
            user,
            itemId,
            itemTitle
    );
});

        Button backButton = new Button("Back");

        backButton.setOnAction(event ->
                Dashboard.show(stage, user)
        );

       VBox root = new VBox(
        15,
        title,
        searchField,
        typeBox,
        statusBox,
        searchButton,
        table,
        claimButton,
        backButton
);

        root.setPadding(new Insets(25));

        Scene scene = new Scene(root, 1000, 650);

        Theme.apply(scene);

        stage.setTitle("Search Items");
        stage.setScene(scene);
    }
}