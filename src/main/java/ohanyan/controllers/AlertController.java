package ohanyan.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;


@RequiredArgsConstructor
@Component
@FxmlView("alert.fxml")
public class AlertController {
    public enum AlertType {
        INFO, WARN, ERROR
    }

    public void show(String title, String header, String content, AlertType type, GridPane expContent) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText(header);
        dialog.setTitle(title);
        switch (type) {
            case INFO:
                try {
                    InputStream image = this.getClass().getResource("/images/exclamation-mark.png").openStream();
                    Image icon = new Image(image);
                    image.close();
                    dialog.setGraphic(new ImageView(icon));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                break;
            case ERROR:
                try {
                    InputStream image = this.getClass().getResource("/images/error.png").openStream();
                    Image icon = new Image(image);
                    image.close();
                    dialog.setGraphic(new ImageView(icon));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                break;
            case WARN:
                break;
        }
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(content);
        if (expContent != null) {
            dialog.getDialogPane().setExpandableContent(expContent);
        }
        dialog.getDialogPane().getButtonTypes().add(ok);
        dialog.showAndWait();

    }


}
