package ohanyan.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@FxmlView("alert.fxml")
public class AlertController {
    enum AlertType {
        INFO, WARN, ERROR
    }

    public static final String CHOOSE_ERROR = "Ошибка выбора";
    public static final String CHOOSE_TASK = "Выберите задачу";
    public static final String DATA_ERROR = "Ошибка data";
    public static final String DATA_DELETE_ERROR = "Ошибка удаления данных";
    public static final String NO_RIGHTS = "Недостаточный уровень прав";
    public static final String NO_RIGHTS_FOR_ACTION = "Недостаточно прав для выполнения действия";
    public static final String ACCESS_ERROR = "Ошибка доступа";
    public static final String EDIT_ERROR = "Ошибка редактирования";
    public static final String CHOOSE_VALUE_IN_TABLE = "Выберите значение в таблице";
    public static final String NO_ROLE_FOR_LOOK_UP = "Ваша роль в системе не позволяет просматривать выбранные данные.";
    public static final String NO_ROLE_FOR_MANAGE_DATA = "Ваша роль в системе не позволяет управлять выбранными данными.";
    @FXML
    public VBox alertPane;
    private Stage stage;
    private final Label header = new Label();
    private final Label content = new Label();
    private final Separator separator = new Separator(Orientation.HORIZONTAL);

    @FXML
    private void initialize() {
        VBox vBox = new VBox(header, separator, content);
        alertPane.getChildren().add(vBox);
        stage = new Stage();
        stage.setScene(new Scene(alertPane));
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

    public void show(String title, String header, String content, AlertType type) {
        setTitle(title);
        setHeader(header);
        setContent(content);
        stage.showAndWait();
    }

}
