package ohanyan.controllers;

import javafx.fxml.FXML;
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
import java.util.List;

@RequiredArgsConstructor
@Component
@FxmlView("uploadCsv.fxml")
public class UploadCsvController {

    private final DBconnection dBconnection;

    private final CsvImportedRepository csvImportedRepository;
    File csvFile = null;
    private Stage stage;
    @FXML
    public Label alreadyImported;
    @FXML
    public Button uploadCsv;

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
                alreadyImported.setText("Report for that period already imported");
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
