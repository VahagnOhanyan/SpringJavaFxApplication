package ohanyan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@FxmlView("dialog.fxml")
public class DialogController {
    enum DialogType {
        QUESTION, WARNING
    }

    public static String result;
    public void show(String title, String header, String content, DialogType type) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText(header);
        dialog.setTitle(title);
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.setContentText(content);
        switch (type) {
            case QUESTION:
                try {
                    InputStream image = this.getClass().getResource("/images/question.png").openStream();
                    Image icon = new Image(image);
                    image.close();
                    dialog.setGraphic(new ImageView(icon));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case WARNING:
                try {
                    InputStream image = this.getClass().getResource("/images/warning.png").openStream();
                    Image icon = new Image(image);
                    image.close();
                    dialog.setGraphic(new ImageView(icon));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

        dialog.getDialogPane().getButtonTypes().add(ok);
        dialog.getDialogPane().getButtonTypes().add(cancel);
        Optional<ButtonType> buttonType = dialog.showAndWait();
        if (buttonType.isPresent()) {
            ButtonType res = buttonType.get();
            if (res == ok) {
                result = "confirm";
            } else {
                result = "cancel";
            }
        }
    }

}