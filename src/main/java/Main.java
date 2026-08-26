import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

public class Main extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));
    private Duke duke = new Duke();

    @Override
    public void start(Stage stage) {
        //Setting up required components

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        HBox inputBar = new HBox(5.0, userInput, sendButton);
        inputBar.setPrefHeight(35.0);

        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, inputBar);

        stage.setTitle("Duke");
        stage.setResizable(false);

        mainLayout.setPrefSize(400.0, 600.0);

        // Set the scene size before showing the stage. This avoids the
        // compositor choosing a default size (especially on Wayland).
        scene = new Scene(mainLayout, 400.0, 600.0);
        stage.setScene(scene);

        scrollPane.setPrefSize(385, 560);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        userInput.setPrefWidth(325.0);
        userInput.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(userInput, javafx.scene.layout.Priority.ALWAYS);

        sendButton.setPrefSize(55.0, 30.0);
        sendButton.setMinSize(55.0, 30.0);
        sendButton.setMaxSize(55.0, 30.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(scrollPane, 1.0);
        AnchorPane.setRightAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(scrollPane, 40.0);

        AnchorPane.setLeftAnchor(inputBar, 1.0);
        AnchorPane.setRightAnchor(inputBar, 1.0);
        AnchorPane.setBottomAnchor(inputBar, 1.0);

        stage.show();

    }

    private void handleUserInput() {
        String userText = userInput.getText();
        String dukeText = duke.getResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getDukeDialog(dukeText, dukeImage)
        );

        userInput.clear();
    }
}
