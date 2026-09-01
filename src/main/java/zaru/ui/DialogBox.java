package zaru.ui;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import zaru.parser.Response;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert dialog != null : "Dialog label should have been injected by FXML.";
        assert displayPicture != null : "Display picture should have been injected by FXML.";

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        dialog.getStyleClass().add("reply-label");
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for user input.
     *
     * @param text User input to display.
     * @param img User display image.
     * @return Dialog box for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a dialog box for a chatbot response.
     *
     * @param response Chatbot response to display.
     * @param img Chatbot display image.
     * @return Dialog box for the chatbot.
     */
    public static DialogBox getZaruDialog(Response response, Image img) {
        assert response != null : "Dialog box requires a chatbot response.";

        var db = new DialogBox(response.getText(), img);
        db.flip();
        db.changeDialogStyle(response.getType());
        return db;
    }

    private void changeDialogStyle(Response.ResponseType responseType) {
        if (Objects.requireNonNull(responseType) == Response.ResponseType.ERROR) {
            dialog.getStyleClass().add("error-label");
        }
    }
}
