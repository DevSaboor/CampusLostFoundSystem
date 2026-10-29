package ui;

import dao.MyReportsDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.util.List;

public class MyReports {

    public static void show(Stage stage, User user) {

        Label title = new Label("My Reports");

        title.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        TableView<String[]> table = new TableView<>();

        TableColumn<String[], String> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[0])
        );

        TableColumn<String[], String> titleColumn =
                new TableColumn<>("Title");

        titleColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[1])
        );

        TableColumn<String[], String> categoryColumn =
                new TableColumn<>("Category");

        categoryColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[2])
        );

        TableColumn<String[], String> locationColumn =
                new TableColumn<>("Location");

        locationColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[3])
        );

        TableColumn<String[], String> dateColumn =
                new TableColumn<>("Date");

        dateColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[4])
        );

        TableColumn<String[], String> typeColumn =
                new TableColumn<>("Type");

        typeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[5])
        );

        TableColumn<String[], String> statusColumn =
                new TableColumn<>("Status");

        statusColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue()[6])
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

        MyReportsDAO dao = new MyReportsDAO();

        List<String[]> reports =
                dao.getMyReports(user.getUserId());

        table.setItems(
                FXCollections.observableArrayList(reports)
        );

        Button refreshButton = new Button("Refresh");

        refreshButton.setOnAction(event -> {

            List<String[]> updatedReports =
                    dao.getMyReports(user.getUserId());

            table.setItems(
                    FXCollections.observableArrayList(updatedReports)
            );
        });

        Button backButton = new Button("Back");

        backButton.setOnAction(event ->
                Dashboard.show(stage, user)
        );

        VBox root = new VBox(
                15,
                title,
                table,
                refreshButton,
                backButton
        );

        root.setPadding(new Insets(25));

        Scene scene = new Scene(root, 950, 600);

        Theme.apply(scene);

        stage.setTitle("My Reports");
        stage.setScene(scene);
    }
}