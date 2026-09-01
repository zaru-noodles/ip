package zaru.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import zaru.Zaru;
import zaru.parser.Response;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Zaru zaru;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image zaruImage = new Image(this.getClass().getResourceAsStream("/images/Zaru.png"));

    /** Initializes bindings after FXML fields have been injected. */
    @FXML
    public void initialize() {
        assert scrollPane != null : "Scroll pane should have been injected by FXML.";
        assert dialogContainer != null : "Dialog container should have been injected by FXML.";

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the chatbot instance.
     *
     * @param z Chatbot used to respond to user input.
     */
    public void setZaru(Zaru z) {
        assert z != null : "Main window requires a Zaru instance.";

        zaru = z;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Zaru's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert zaru != null : "Zaru should have been injected before handling input.";
        assert userInput != null : "User input field should have been injected by FXML.";
        assert dialogContainer != null : "Dialog container should have been injected by FXML.";

        String input = userInput.getText();
        Response response = zaru.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getZaruDialog(response, zaruImage)
        );
        userInput.clear();
    }
}
