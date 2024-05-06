package ohanyan.code.custom;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import ohanyan.controllers.TableColumnController;
import ohanyan.domain.StatusEntity;
import ohanyan.repo.StatusRepository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ComboBoxCell extends TableCell<TableColumnController, String> {


    private final ComboBox<String> comboBox = new ComboBox<>();
    private final TreeView<String> treeView;

    TableColumn<TableColumnController, String> tableCol;
    int index;
    String prevNode;
    private final StatusRepository statusRepository;
    Map<Integer, Map<String, String>> changesMap;
    Button btnChangesApply;
    Button btnChangesCancel;

    public ComboBoxCell(TreeView<String> treeView, Button btnChangesApply, Button btnChangesCancel, Map<Integer, Map<String, String>> changes, TableColumn<TableColumnController, String> tableCol, String prevNode, StatusRepository statusRepository) {
        changesMap = changes;
        this.tableCol = tableCol;
        this.prevNode = prevNode;
        this.statusRepository = statusRepository;
        this.treeView = treeView;
        this.btnChangesApply = btnChangesApply;
        this.btnChangesCancel = btnChangesCancel;
    }
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(item);
        if (prevNode.equals("Мои задачи") || prevNode.equals("В работе") || prevNode.equals("Выполнено") || prevNode.equals("Проверено") ||
                prevNode.equals("Утверждено") || prevNode.equals("В ожидании")) {
            if (tableCol.getText().equals("Статус") || tableCol.getText().equals("Ед. изм. задачи")) {
              //  setPrefWidth(120);
            } else {
                setStyle("-fx-alignment: CENTER;");
            }
        }

    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (getTableRow() != null && getTableRow().getIndex() >= 0) {
            index = getTableRow().getIndex();
        }
        if (tableCol.getText().equals("Статус")) {
            comboBox.setItems(FXCollections.observableArrayList(statusRepository.findAll().stream().map(StatusEntity::getStatusName).collect(Collectors.toList())));
        }
        comboBox.getItems().add(null);
        comboBox.setOnAction(event -> {
            Map<String, String> rowColumns;
            if (!changesMap.containsKey(index)) {
                rowColumns = new HashMap<>();
                changesMap.put(index, rowColumns);
            } else {
                rowColumns = changesMap.get(index);
            }
            rowColumns.put(tableCol.getText(), comboBox.getValue());
            System.out.println("changesMap: " + changesMap);
            ArrayList<String> removeList = new ArrayList<>();
            for (Map.Entry<String, String> column :
                    rowColumns.entrySet()) {
                boolean contains = false;
                if (column.getKey().equals("Статус")) {
                    contains = getTableRow().getItem().toString().contains("status_name='" + column.getValue() + "'");
                }
                if (contains) {
                    System.out.println("remove status");
                    removeList.add("Статус");
                    continue;
                }
                if (column.getKey().equals("Ед. изм. задачи")) {
                    contains = getTableRow().getItem().toString().contains("task_uom_name='" + column.getValue() + "'");
                }
                if (contains) {
                    System.out.println("remove task_uom_name");
                    removeList.add("Ед. изм. задачи");
                    continue;
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

        });
        System.out.println("exit");
        if (comboBox.getValue() == null) {
            comboBox.setValue(getText());
        }
        setGraphic(comboBox);
        setText(null);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
        setText(comboBox.getValue());
        setItem(comboBox.getValue());
        // comboBox.setValue(String.valueOf(getItem()));
      /*  setGraphic(comboBox);
        setText(null);*/

    }
}
