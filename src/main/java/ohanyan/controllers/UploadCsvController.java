package ohanyan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import ohanyan.adapter.jdbc.DBconnection;
import ohanyan.code.CSVLoader;
import ohanyan.domain.CsvImportedEntity;
import ohanyan.repo.CsvImportedRepository;
import ohanyan.repo.CsvRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Component
@FxmlView("importOneCReport.fxml")
public class UploadCsvController {

    private final CsvImportedRepository csvImportedRepository;
    private final CsvRepository csvRepository;
    @FXML
    public Label alreadyImported;
    @FXML
    public Label noReportImported;

    @FXML
    public Button uploadCsv;
    private final DBconnection dBconnection;

    File csvFile = null;

    private Stage stage;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private void initialize() {

        stage = new Stage();
        stage.setScene(new Scene(anchorPane));
        stage.setMinHeight(100);
        stage.setMinWidth(200);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
    }

    public void show(String title) {
        stage.setTitle(title);
        stage.showAndWait();
    }

    public void browse() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        csvFile = fileChooser.showOpenDialog(new Stage());
        if (csvFile == null) {
            return;
        }
        String csvFileName = csvFile.getName();
        int dotIndex = csvFileName.lastIndexOf('.');
        csvFileName = (dotIndex == -1) ? csvFileName : csvFileName.substring(0, dotIndex);
        csvFileName = csvFileName.replace("-", ".");
        List<CsvImportedEntity> records = csvImportedRepository.findAll();
        for (int i = 0; i < records.size(); i++) {
            if (csvFileName.equals(records.get(i).getMonthyear())) {
                csvFile = null;
                alreadyImported.setText("Отчёт за данный период уже загружен");
                alreadyImported.setVisible(true);
            }
        }

        uploadCsv.setDisable(csvFile == null);
    }

    public void uploadCsv() {
        dBconnection.openDB();
        CSVLoader csvLoader = new CSVLoader(dBconnection.getC());
        try {
            csvLoader.loadCSV(csvFile, "csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
        uploadCsv.setDisable(true);
    }

}
