package ohanyan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@FxmlView("dialog.fxml")
public class DialogController {
    @FXML
    public AnchorPane root;
    @FXML
    public Button confirm;
    @FXML
    public Button cancel;

    private Stage stage;
    private final Label header = new Label();
    private final Label content = new Label();
    private final Separator separator = new Separator(Orientation.HORIZONTAL);
    public static String result;

    @FXML
    private void initialize() {
        VBox vBox = new VBox(header, separator, content);
        root.getChildren().add(vBox);
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);

    }

    public void setTitle(String title) {
        stage.setTitle(title);
    }

    public void setHeader(String header) {
        this.header.setText(header);
    }

    public void setContent(String content) {
        this.content.setText(content);
    }


    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "confirm":
                result = "confirm";
                stage.close();
                break;
            case "cancel":
                result = "cancel";
                stage.close();
                break;
        }
    }

    public void show(String title, String header, String content) {
        setTitle(title);
        setHeader(header);
        setContent(content);
        stage.showAndWait();
    }

}