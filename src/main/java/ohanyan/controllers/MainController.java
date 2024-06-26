package ohanyan.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import ohanyan.adapter.jdbc.DBconnection;
import ohanyan.code.custom.*;
import ohanyan.code.custom.TreeCell;
import ohanyan.repo.StatusRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

import static javafx.scene.control.TableView.UNCONSTRAINED_RESIZE_POLICY;
import static ohanyan.controllers.FxmlViewAccessController.getTranslatedValue;

@RequiredArgsConstructor
@Component
@FxmlView("main.fxml")
public class MainController {

    public static String role;
    public static Map<String, Integer> authorities;
    public static String who;

    @FXML
    public TreeView<String> treeView;

    @FXML
    public TableView<TableColumnController> dataView;

    @FXML
    public Button deleteButton;
    @FXML
    public Button viewButton;
    @FXML
    public Button editButton;
    private final StatusRepository statusRepository;
    private final DBconnection dBconnection;
    private final DialogController dialogController;
    private final AlertController alertController;
    private Stage stage;
    @FXML
    public VBox mainVBox;
    private final FxWeaver fxWeaver;
    private String sql = "";
    private final List<String> columnList = new ArrayList<>();
    private ObservableList<TableColumnController> data = FXCollections.observableArrayList();
    private String nodeName = "";
    Map<Integer, Map<String, String>> changes = new HashMap<>();
    @FXML
    public Button btnChangesApply;
    @FXML
    public Button btnChangesCancel;
    @FXML
    public Button backButton;
    @FXML
    public Button forwardButton;
    ArrayList<Object> history = new ArrayList<>();
    private int backCount = 1;
    private boolean isBack = false;
    private boolean isForward = false;
    private String tableColType = "";

    @FXML
    private void initialize() {
        stage = new Stage();
        stage.setTitle("v 1.0.0");
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setScene(new Scene(mainVBox));
        stage.initModality(Modality.WINDOW_MODAL);
        FxmlViewAccessController.resolveAccess(mainVBox);
        InputStream image = null;
        try {
            image = this.getClass().getResource("/images/back.png").openStream();
            Image backImage = new Image(image);
            image.close();
            image = this.getClass().getResource("/images/forward.png").openStream();
            Image forwardImage = new Image(image);
            image.close();
            backButton.setTooltip(new Tooltip("Назад"));
            backButton.graphicProperty().setValue(new ImageView(backImage));
            forwardButton.setTooltip(new Tooltip("Вперёд"));
            forwardButton.graphicProperty().setValue(new ImageView(forwardImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!isBack && !isForward) {
                history.add(newValue);
            }
            isBack = false;
            isForward = false;
            backButton.setDisable(backCount == history.size());
            dataView.getSelectionModel().clearSelection();
            deleteButton.setDisable(true);
            viewButton.setDisable(true);
            editButton.setDisable(true);
            nodeName = newValue.getValue();
            generateTableView(newValue.getValue());
        });
        dataView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!dataView.getSelectionModel().isEmpty()) {
                checkEditAccesses(nodeName);
            }
        });
        dataView.setOnMouseClicked(event -> {
            Object src = event.getSource();
            if (!(src instanceof TableView)) {
                return;
            }

            if (event.getClickCount() == 2) {
                for (String key : ContextController.authorities.keySet()) {
                    if (getTranslatedValue(nodeName) != null && key.contains(getTranslatedValue(nodeName)) && key.startsWith("assign")) {
                        System.out.println("key: " + key);
                        int accessType = ContextController.authorities.get(key);
                        System.out.println("accessType: " + accessType);
                        if (accessType == 1) {
                            // open assign window
                            break;
                        }
                    }
                }

            } else {
                // open assign window
            }

        });
    }

    public void show() {
        stage.show();
    }

    private void generateTableView(String newValue) {
        switch (newValue) {
            case "Менеджмент":
                sql = "";
                break;
            case "My projects":
                sql = "SELECT p.project_id, p.project_name, p.customer_id FROM public.project p " +
                        "ORDER BY p.project_id";
                break;
            case "Employees":
                sql = "select user_fullname, user_tel, user_address, user_email from public.user";
                break;
        }
        dataView.setEditable(true);
        dataView.getSelectionModel().clearSelection();
        dataView.getColumns().clear();
        dataView.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
        data.clear();
        System.out.println(sql);
        dBconnection.openDB();
        dBconnection.query(sql);
        try {
            ObservableList<TableColumn<TableColumnController, ?>> columns = dataView.getColumns();
            columnList.clear();
            columns.clear();
            int columnSize = dBconnection.getRs().getMetaData().getColumnCount();
            TableColumn<TableColumnController, String>[] tableColumns = new TableColumn[columnSize];
            fillTableColumnList(columnSize);
            for (int i = 1; i <= columnList.size(); i++) {
                String tableColName = columnList.get(i - 1);
                tableColName = TableColumnController.generateColName(tableColName);
                tableColType = ControlController.defineColumnType(newValue, tableColName);
                tableColumns[i - 1] = new TableColumn<>(tableColName);
                tableColumns[i - 1].setCellValueFactory(new PropertyValueFactory<>(columnList.get(i - 1)));
                if (tableColType.equals("ComboBox")) {
                    tableColumns[i - 1].setCellFactory(c ->
                            new ComboBoxCell(treeView, btnChangesApply, btnChangesCancel, changes, c, newValue, statusRepository));
                } else if (tableColType.equals("DatePicker")) {
                    tableColumns[i - 1].setCellFactory(c -> new DatePickerCell(treeView, btnChangesApply, btnChangesCancel, changes, c, newValue, alertController));
                } else if (tableColType.equals("CheckBox")) {
                    tableColumns[i - 1].setCellFactory(c -> new CheckBoxCell(treeView, btnChangesApply, btnChangesCancel, changes, c, newValue));
                } else if (tableColType.equals("TextField")) {
                    tableColumns[i - 1].setCellFactory(c -> new TextFieldCell(treeView, btnChangesApply, btnChangesCancel, changes, c, newValue));
                } else {
                    tableColumns[i - 1].setCellFactory(c -> new TreeCell<>(c, newValue));
                }

            }
            if (columnList.size() > 0) {
                Collections.addAll(columns, tableColumns);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillTableColumnList(int columnSize) throws SQLException {
        while (dBconnection.getRs().next()) {
            TableColumnController c = new TableColumnController();
            for (int i = 1; i <= columnSize; i++) {
                String value = dBconnection.getRs().getString(i);
                String column = dBconnection.getRs().getMetaData().getColumnName(i);
                columnList.add(column);
                c.setTableColValue(column, value);
            }
            data.add(c);
        }
        dBconnection.queryClose();
        dBconnection.closeDB();
        dataView.setItems(data);

    }


    private void checkEditAccesses(String nodeName) {
        if (!dataView.getSelectionModel().isEmpty()) {
            for (String key : authorities.keySet()) {
                if (key.startsWith("view" + nodeName)) {
                    int accessType = authorities.get(key);
                    if (accessType == 4) {
                        viewButton.setDisable(true);
                    } else if (accessType == 1) {
                        viewButton.setDisable(false);
                    }
                    break;
                } else {
                    viewButton.setDisable(true);
                }
                if (key.startsWith("edit" + nodeName)) {
                    int accessType = authorities.get(key);
                    if (accessType == 4) {
                        editButton.setDisable(true);
                    } else if (accessType == 1) {
                        editButton.setDisable(false);
                    }
                    break;
                } else {
                    editButton.setDisable(true);
                }
                if (key.startsWith("delete" + nodeName)) {
                    int accessType = authorities.get(key);
                    if (accessType == 4) {
                        deleteButton.setDisable(true);
                    } else if (accessType == 1) {
                        deleteButton.setDisable(false);
                    }
                    break;
                } else {
                    deleteButton.setDisable(true);
                }
            }
        }
    }

    public void actionMenuItemPressed(ActionEvent actionEvent) throws SQLException {

        Object source = actionEvent.getSource();
        if (!(source instanceof MenuItem)) {
            return;
        }

        MenuItem clickedMenuItem = (MenuItem) source;

        switch (clickedMenuItem.getId()) {
            case "editCalendar":
                setCalendarStage();
                break;
            case "editAccess":
                setAccessStage();
                break;
            case "uploadCsv":
                setUploadCsvStage();
                break;
            case "report":
                setReportStage();
                break;
        }
    }

    private void setCalendarStage() {
        fxWeaver.loadController(CalendarController.class).show();
    }

    private void setAccessStage() {
        fxWeaver.loadController(AccessController.class).show("super_user");
    }

    private void setUploadCsvStage() {
        fxWeaver.loadController(UploadCsvController.class).show("Upload CSV file");
    }

    private void setReportStage() {
        fxWeaver.loadController(ReportController.class).show("Report");
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        if (!clickedButton.getId().equals("backButton") &&
                !clickedButton.getId().equals("forwardButton") && backCount > 1) {
            backCount++;
        }
        switch (clickedButton.getId()) {
            case "backButton":
                backCount++;
                if (history.get(history.size() - backCount) instanceof Controller) {
                    Controller c = (Controller) history.get(history.size() - backCount);
                    c.showThis();
                }

                isBack = true;
                if (history.get(history.size() - backCount) instanceof TreeItem) {
                    if (((String) ((TreeItem) history.get(history.size() - backCount)).getValue()).equalsIgnoreCase(treeView.getSelectionModel().getSelectedItem().getValue())) {
                        backCount++;
                    }
                    treeView.getSelectionModel().select((TreeItem) history.get(history.size() - backCount));
                }

                backButton.setDisable(backCount == history.size());
                forwardButton.setDisable(backCount == 1);
                break;
            case "forwardButton":
                backCount--;
                isForward = true;
                if (history.get(history.size() - backCount) instanceof Controller) {
                    Controller c = (Controller) history.get(history.size() - backCount);
                    c.showThis();
                }
                if (history.get(history.size() - backCount) instanceof TreeItem) {
                    treeView.getSelectionModel().select((TreeItem) history.get(history.size() - backCount));
                }
                forwardButton.setDisable(backCount == 1);
                break;
        }

    }
}