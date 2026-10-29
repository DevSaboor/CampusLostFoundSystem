package ui;

import dao.ClaimDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class SubmitClaim {

    public static void show(
            Stage stage,
            User user,
            int itemId,
            String itemTitle
    ) {

        Label title = new Label("Submit Claim");

        title.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        Label itemLabel = new Label(
                "Claiming: " + itemTitle +
                " (Item #" + itemId + ")"
        );

        TextArea description = new TextArea();

        description.setPromptText(
                "Explain why this item belongs to you..."
        );

        description.setWrapText(true);
        description.setPrefRowCount(6);

        Button submitButton =
                new Button("Submit Claim");

        Button backButton =
                new Button("Back");

        Label message = new Label();

        submitButton.setPrefWidth(220);
        backButton.setPrefWidth(220);

        submitButton.setOnAction(event -> {

            String claimText =
                    description.getText().trim();

            if (claimText.isEmpty()) {

                message.setText(
                        "Please describe your claim."
                );

                return;
            }

            ClaimDAO claimDAO =
                    new ClaimDAO();

            boolean success =
                    claimDAO.submitClaim(
                            itemId,
                            user.getUserId(),
                            claimText
                    );

            if (success) {

                message.setText(
                        "Claim submitted successfully!"
                );

                description.clear();

            } else {

                message.setText(
                        "Unable to submit claim."
                );
            }
        });

        backButton.setOnAction(event ->
                SearchItems.show(stage, user)
        );

        VBox root = new VBox(
                15,
                title,
                itemLabel,
                description,
                submitButton,
                backButton,
                message
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        Scene scene =
                new Scene(root, 650, 550);

                Theme.apply(scene);

        stage.setTitle("Submit Claim");
        stage.setScene(scene);
    }
}