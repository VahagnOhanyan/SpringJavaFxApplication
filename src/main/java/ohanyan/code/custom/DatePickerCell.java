package ohanyan.code.custom;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ohanyan.controllers.AlertController;
import ohanyan.controllers.TableColumnController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatePickerCell extends TableCell<TableColumnController, String> {


    private final DatePicker datePicker = new DatePicker();
    private final TreeView<String> treeView;

    TableColumn<TableColumnController, String> tableCol;
    int index;
    String prevNode;
    Map<Integer, Map<String, String>> changesMap;
    Button btnChangesApply;
    Button btnChangesCancel;
    AlertController alertController;
    private boolean isValid = true;

    public DatePickerCell(TreeView<String> treeView, Button btnChangesApply, Button btnChangesCancel, Map<Integer, Map<String, String>> changes, TableColumn<TableColumnController, String> tableCol, String prevNode, AlertController alertController) {
        changesMap = changes;
        this.tableCol = tableCol;
        this.prevNode = prevNode;
        this.treeView = treeView;
        this.btnChangesApply = btnChangesApply;
        this.btnChangesCancel = btnChangesCancel;
        this.alertController = alertController;


    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(item);
        if (prevNode.equals("Мои задачи") || prevNode.equals("В работе") || prevNode.equals("Выполнено") || prevNode.equals("Проверено") ||
                prevNode.equals("Утверждено") || prevNode.equals("В ожидании")) {
            if (tableCol.getText().equals("Дата получения")) {
               // setPrefWidth(120);
                System.out.println("CENTER-RIGHT");
                setStyle("-fx-alignment: CENTER-RIGHT;");
            }
        }

    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (getTableRow() != null && getTableRow().getIndex() >= 0) {
            index = getTableRow().getIndex();

        }
        datePicker.editorProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if (LocalDate.parse(newValue.getText()).isAfter(LocalDate.now())) {
                System.out.println("isAfter");
                //  if(datePicker.getValue().isAfter(LocalDate.now()) ){
                setAlertStage(null, null, "Вы ввели не корректное значение для даты получения: значение не должно превышать текущую дату", AlertController.AlertType.ERROR, null);
            }
        });
        datePicker.setOnAction(event -> {
            if (!datePicker.getValue().isAfter(LocalDate.now())) {
                Map<String, String> rowColumns;
                if (!changesMap.containsKey(index)) {
                    rowColumns = new HashMap<>();
                    changesMap.put(index, rowColumns);
                } else {
                    rowColumns = changesMap.get(index);
                }
                rowColumns.put(tableCol.getText(), String.valueOf(datePicker.getValue()));
                System.out.println("changesMap: " + changesMap);

                ArrayList<String> removeList = new ArrayList<>();
                for (Map.Entry<String, String> column :
                        rowColumns.entrySet()) {
                    boolean contains = false;
                    if (column.getKey().equals("Дата получения")) {
                        contains = getTableRow().getItem().toString().contains("task_income_date='" + column.getValue() + "'");
                    }
                    if (contains) {
                        System.out.println("remove task_income_date");
                        removeList.add("Дата получения");
                        break;
                    }
                }
                for (String column :
                        removeList) {
                    rowColumns.remove(column);
                }
                removeList.clear();
                boolean isEmpty = true;
                for (Map.Entry<Integer, Map<String, String>> kv :
                        changesMap.entrySet()) {
                    if (!kv.getValue().isEmpty()) {
                        isEmpty = false;
                        break;
                    }
                }
                if (isEmpty) {
                    System.out.println("isEmpty");
                    treeView.setDisable(false);
                    btnChangesApply.setDisable(true);
                    btnChangesCancel.setDisable(true);
                } else {
                    treeView.setDisable(true);
                    btnChangesApply.setDisable(false);
                    btnChangesCancel.setDisable(false);
                }
                isValid = true;
            } else {
                setAlertStage(null, null, "Вы ввели не корректное значение для даты получения: значение не должно превышать текущую дату", AlertController.AlertType.ERROR, null);
                isValid = false;
            }
        });

        if (datePicker.getValue() == null) {
            datePicker.setValue(LocalDate.parse(getText()));
        }
        setGraphic(datePicker);


        setText(null);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
     /*   if(datePicker.getValue().isAfter(LocalDate.now()) ){
           // setAlertStage(null, null, "Вы ввели не корректное значение для конечной даты: значение не должно отличаться от исходного эначения больше чем на единицу", AlertController.AlertType.ERROR, null);

        }else {*/
        if (isValid) {
            setGraphic(null);
            setText(String.valueOf(datePicker.getValue()));
            setItem(String.valueOf(datePicker.getValue()));
        } else {
            System.out.println("inValid");
            System.out.println(getItem());
            setGraphic(null);
            setText(getItem());
            setItem(getItem());
            datePicker.setValue(LocalDate.parse(getItem()));
        }
        //  }
        // comboBox.setValue(String.valueOf(getItem()));
      /*  setGraphic(comboBox);
        setText(null);*/

    }

    private void setAlertStage(String title, String header, String content, AlertController.AlertType type, GridPane expContent) {
        alertController.show(title, header, content, type, expContent);
    }
}
