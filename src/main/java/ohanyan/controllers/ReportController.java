package ohanyan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

@RequiredArgsConstructor
@Component
@FxmlView("report.fxml")
public class ReportController {

    @FXML
    private ComboBox<String> yearBox;
    @FXML
    private ComboBox<String> halfyearBox;
    @FXML
    private ComboBox<String> quarterBox;
    @FXML
    private ComboBox<String> monthBox;
    @FXML
    private RadioButton reportSwitch;
    @FXML
    private RadioButton reportSwitchForPeriod;
    @FXML
    private RadioButton reportSwitchForQuarter;
    @FXML
    private RadioButton reportSwitchForMonth;
    @FXML
    private RadioButton reportSwitchForYear;
    @FXML
    private RadioButton reportSwitchForHalfYear;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    LocalDate firstQuarterStart = LocalDate.of(LocalDate.now().getYear(), Month.of(1), 1);
    LocalDate firstQuarterEnd = LocalDate.of(LocalDate.now().getYear(), Month.of(3), 31);
    LocalDate secondQuarterStart = LocalDate.of(LocalDate.now().getYear(), Month.of(4), 1);
    LocalDate secondQuarterEnd = LocalDate.of(LocalDate.now().getYear(), Month.of(6), 30);
    LocalDate thirdQuarterStart = LocalDate.of(LocalDate.now().getYear(), Month.of(7), 1);
    LocalDate thirdQuarterEnd = LocalDate.of(LocalDate.now().getYear(), Month.of(9), 30);
    LocalDate fourthQuarterStart = LocalDate.of(LocalDate.now().getYear(), Month.of(10), 1);
    LocalDate fourthQuarterEnd = LocalDate.of(LocalDate.now().getYear(), Month.of(12), 31);


    private Stage stage;
    @FXML
    public AnchorPane anchorPane;
    @FXML

    private static final String pattern = "yyyy-MM-dd";

    @FXML
    private void initialize() {
        stage = new Stage();
        stage.setScene(new Scene(anchorPane));
        stage.setMinHeight(100);
        stage.setMinWidth(200);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);

        yearBox.getItems().addAll("2018", "2019", "2020", "2021", "2022");
        halfyearBox.getItems().addAll("I", "II");
        quarterBox.getItems().addAll("I", "II", "III", "IV");
        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        monthBox.getItems().addAll(months);
        reportSwitch.setSelected(true);

        reportSwitch.selectedProperty().addListener(observable -> {
            if (reportSwitch.isSelected()) {
                startDate.setVisible(true);
                startDate.setDisable(true);
                monthBox.setVisible(false);
                quarterBox.setVisible(false);
                halfyearBox.setVisible(false);
                yearBox.setVisible(false);
                endDate.setVisible(true);
                startDate.setValue(LocalDate.of(2018, 1, 1));
                endDate.setValue(LocalDate.now());
            }
        });

        reportSwitchForPeriod.selectedProperty().addListener(observable -> {
            if (reportSwitchForPeriod.isSelected()) {
                monthBox.setVisible(false);
                quarterBox.setVisible(false);
                halfyearBox.setVisible(false);
                yearBox.setVisible(false);
                startDate.setVisible(true);
                startDate.setDisable(false);
                endDate.setVisible(true);
                endDate.setPromptText("по");
                startDate.setValue(LocalDate.now());
                endDate.setValue(LocalDate.now());
            }
        });

        reportSwitchForYear.selectedProperty().addListener(observable -> {
            if (reportSwitchForYear.isSelected()) {
                startDate.setVisible(true);
                startDate.setDisable(true);
                endDate.setVisible(false);
                monthBox.setVisible(false);
                quarterBox.setVisible(false);
                halfyearBox.setVisible(false);
                startDate.setValue(LocalDate.of(LocalDate.now().getYear(), Month.of(1), 1));
                yearBox.setVisible(true);
                yearBox.setValue(String.valueOf(LocalDate.now().getYear()));
                endDate.setValue(LocalDate.now());
            }
        });
        reportSwitchForHalfYear.selectedProperty().addListener(observable -> {
            if (reportSwitchForHalfYear.isSelected()) {
                startDate.setVisible(false);
                endDate.setVisible(false);
                monthBox.setVisible(false);
                quarterBox.setVisible(false);
                halfyearBox.setVisible(true);
                if (LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), Month.of(6), 30))) {
                    halfyearBox.setValue("I");
                    startDate.setValue(LocalDate.of(LocalDate.now().getYear(), Month.of(1), 1));
                } else {
                    halfyearBox.setValue("II");
                    startDate.setValue(LocalDate.of(LocalDate.now().getYear(), Month.of(7), 1));
                }
                yearBox.setVisible(true);
                yearBox.setValue(String.valueOf(LocalDate.now().getYear()));
                endDate.setValue(LocalDate.now());
            }
        });

        reportSwitchForQuarter.selectedProperty().addListener(observable -> {
            if (reportSwitchForQuarter.isSelected()) {
                startDate.setVisible(false);
                endDate.setVisible(false);
                monthBox.setVisible(false);
                halfyearBox.setVisible(false);
                quarterBox.setVisible(true);
                if (LocalDate.now().isBefore(secondQuarterStart)) {
                    quarterBox.setValue("I");
                    startDate.setValue(firstQuarterStart);
                } else if (LocalDate.now().isBefore(thirdQuarterStart)) {
                    quarterBox.setValue("II");
                    startDate.setValue(secondQuarterStart);
                } else if (LocalDate.now().isBefore(fourthQuarterStart)) {
                    quarterBox.setValue("III");
                    startDate.setValue(thirdQuarterStart);
                } else {
                    quarterBox.setValue("IV");
                    startDate.setValue(fourthQuarterStart);
                }
                endDate.setValue(LocalDate.now());
                yearBox.setVisible(true);
                yearBox.setValue(String.valueOf(LocalDate.now().getYear()));
            }
        });

        reportSwitchForMonth.selectedProperty().addListener(observable -> {
            if (reportSwitchForMonth.isSelected()) {
                startDate.setVisible(false);
                endDate.setVisible(false);
                quarterBox.setVisible(false);
                halfyearBox.setVisible(false);
                monthBox.setVisible(true);
                monthBox.setValue(months[LocalDate.now().getMonth().ordinal()]);
                yearBox.setVisible(true);
                yearBox.setValue(String.valueOf(LocalDate.now().getYear()));
                startDate.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1));
                endDate.setValue(LocalDate.now());
            }
        });

        monthBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                LocalDate oldLdstart = startDate.getValue();
                LocalDate newLdstart = oldLdstart.withMonth(returnMonth(newValue) + 1);
                startDate.setValue(newLdstart);
                defineMaxDayOfMonth(newValue);
                checkIfNowIsWithinRange();
            }
        });

        yearBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                LocalDate oldLdstart = startDate.getValue();
                LocalDate newLdstart = oldLdstart.withYear(Integer.parseInt(newValue));
                startDate.setValue(newLdstart);
                if (monthBox.isVisible()) {
                    defineMaxDayOfMonth(monthBox.getValue());
                } else if (halfyearBox.isVisible()) {
                    defineHalfYearRange(halfyearBox.getValue());
                } else if (quarterBox.isVisible()) {
                    defineQuarterRange(quarterBox.getValue());
                } else {

                    LocalDate oldLdend = endDate.getValue();
                    LocalDate newLdend = oldLdend.withYear(Integer.parseInt(newValue)).withMonth(12).withDayOfMonth(31);
                    endDate.setValue(newLdend);
                }
                checkIfNowIsWithinRange();
            }
        });
        halfyearBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                defineHalfYearRange(newValue);
                checkIfNowIsWithinRange();
            }
        });

        quarterBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                defineQuarterRange(newValue);
                checkIfNowIsWithinRange();
            }
        });

        endDate.valueProperty().addListener((observable) -> {
            System.out.println("startDate: " + startDate.getValue() + " endDate: " + endDate.getValue());
        });

    }

    public void show(String title) {
        stage.setTitle(title);
        stage.showAndWait();
    }

    private void defineQuarterRange(String value) {
        if (value.equals("I")) {
            startDate.setValue(firstQuarterStart.withYear(Integer.parseInt(yearBox.getValue())));
            endDate.setValue(firstQuarterEnd.withYear(Integer.parseInt(yearBox.getValue())));
        }
        if (value.equals("II")) {
            startDate.setValue(secondQuarterStart.withYear(Integer.parseInt(yearBox.getValue())));
            endDate.setValue(secondQuarterEnd.withYear(Integer.parseInt(yearBox.getValue())));
        }
        if (value.equals("III")) {
            startDate.setValue(thirdQuarterStart.withYear(Integer.parseInt(yearBox.getValue())));
            endDate.setValue(thirdQuarterEnd.withYear(Integer.parseInt(yearBox.getValue())));
        }
        if (value.equals("IV")) {
            startDate.setValue(fourthQuarterStart.withYear(Integer.parseInt(yearBox.getValue())));
            endDate.setValue(fourthQuarterEnd.withYear(Integer.parseInt(yearBox.getValue())));
        }
    }

    private void defineHalfYearRange(String value) {
        if (value.equals("I")) {
            startDate.setValue(firstQuarterStart.withYear(Integer.parseInt(yearBox.getValue())));
            endDate.setValue(secondQuarterEnd.withYear(Integer.parseInt(yearBox.getValue())));
        }
        if (value.equals("II")) {
            startDate.setValue(thirdQuarterStart.withYear(Integer.parseInt(yearBox.getValue())));
            endDate.setValue(fourthQuarterEnd.withYear(Integer.parseInt(yearBox.getValue())));
        }
    }

    private void defineMaxDayOfMonth(String value) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(yearBox.getValue()), returnMonth(value), 1);
        int res = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        endDate.setValue(LocalDate.of(Integer.parseInt(yearBox.getValue()), returnMonth(value) + 1, res));
    }

    private void checkIfNowIsWithinRange() {
        if (LocalDate.now().isAfter(startDate.getValue()) && LocalDate.now().isBefore(endDate.getValue())) {
            endDate.setValue(LocalDate.now());
        }
    }


    public void generateReport(ActionEvent actionEvent) {

    }

    public int returnMonth(String month) {
        Integer m = 0;

        switch (month) {
            case "Январь":
                m = 0;
                break;
            case "Февраль":
                m = 1;
                break;
            case "Март":
                m = 2;
                break;
            case "Апрель":
                m = 3;
                break;
            case "Май":
                m = 4;
                break;
            case "Июнь":
                m = 5;
                break;
            case "Июль":
                m = 6;
                break;
            case "Август":
                m = 7;
                break;
            case "Сентябрь":
                m = 8;
                break;
            case "Октябрь":
                m = 9;
                break;
            case "Ноябрь":
                m = 10;
                break;
            case "Декабрь":
                m = 11;
                break;
        }
        return m;
    }

    public void actionClose(ActionEvent actionEvent) {
    }
}