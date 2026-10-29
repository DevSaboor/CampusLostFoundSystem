package ui;

import dao.ItemDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class ReportItem {

    public static void show(Stage stage, User user, String type) {

        String heading = type.equals("LOST")
                ? "Report Lost Item"
                : "Report Found Item";

        Label title = new Label(heading);
        title.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        TextField itemTitle = new TextField();
        itemTitle.setPromptText("Item title");

        TextArea description = new TextArea();
        description.setPromptText("Describe the item");
        description.setPrefRowCount(4);
        description.setWrapText(true);

        ComboBox<String> categoryBox = new ComboBox<>();

        categoryBox.setItems(FXCollections.observableArrayList(
                "Electronics",
                "Documents",
                "Clothing",
                "Accessories",
                "Books",
                "Other"
        ));

        categoryBox.setPromptText("Select category");

        Map<String, Integer> categoryIds = new HashMap<>();

        categoryIds.put("Electronics", 1);
        categoryIds.put("Documents", 2);
        categoryIds.put("Clothing", 3);
        categoryIds.put("Accessories", 4);
        categoryIds.put("Books", 5);
        categoryIds.put("Other", 6);

        TextField location = new TextField();
        location.setPromptText("Where was it lost/found?");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date");

        Button submitButton = new Button("Submit Report");
        Button backButton = new Button("Back");

        submitButton.setPrefWidth(220);
        backButton.setPrefWidth(220);

        Label message = new Label();

        submitButton.setOnAction(event -> {

            String titleText = itemTitle.getText().trim();
            String descriptionText = description.getText().trim();
            String category = categoryBox.getValue();
            String locationText = location.getText().trim();

            if (titleText.isEmpty()
                    || descriptionText.isEmpty()
                    || category == null
                    || locationText.isEmpty()
                    || datePicker.getValue() == null) {

                message.setText("Please complete all fields.");
                return;
            }

            int categoryId = categoryIds.get(category);

            ItemDAO itemDAO = new ItemDAO();

            boolean success = itemDAO.addItem(
                    user.getUserId(),
                    categoryId,
                    titleText,
                    descriptionText,
                    locationText,
                    datePicker.getValue().toString(),
                    type
            );

            if (success) {

                message.setText(
                        type.equals("LOST")
                                ? "Lost item reported successfully!"
                                : "Found item reported successfully!"
                );

                itemTitle.clear();
                description.clear();
                categoryBox.setValue(null);
                location.clear();
                datePicker.setValue(null);

            } else {

                message.setText(
                        "Unable to save report. Please try again."
                );
            }
        });

        backButton.setOnAction(event ->
                Dashboard.show(stage, user)
        );

        VBox root = new VBox(
                15,
                title,
                itemTitle,
                description,
                categoryBox,
                location,
                datePicker,
                submitButton,
                backButton,
                message
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 650, 700);

        Theme.apply(scene);

        stage.setTitle(heading);
        stage.setScene(scene);
    }
}