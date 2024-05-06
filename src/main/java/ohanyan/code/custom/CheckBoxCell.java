package ohanyan.code.custom;

import javafx.scene.control.*;
import ohanyan.controllers.TableColumnController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckBoxCell extends TableCell<TableColumnController, String> {

    boolean firstEdit = true;
    private final CheckBox checkBox = new CheckBox("");
    private final TreeView<String> treeView;

    TableColumn<TableColumnController, String> tableCol;
    int index;
    String prevNode;
    Map<Integer, Map<String, String>> changesMap;
    Button btnChangesApply;
    Button btnChangesCancel;

    public CheckBoxCell(TreeView<String> treeView, Button btnChangesApply, Button btnChangesCancel, Map<Integer, Map<String, String>> changes, TableColumn<TableColumnController, String> tableCol, String prevNode) {
        changesMap = changes;
        this.tableCol = tableCol;
        this.prevNode = prevNode;
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
            if (tableCol.getText().equals("Индивид. план")
                    || tableCol.getText().equals("Аутсорс")) {
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
        checkBox.setOnAction(event -> {
            Map<String, String> rowColumns;
            if (!changesMap.containsKey(index)) {
                rowColumns = new HashMap<>();
                changesMap.put(index, rowColumns);
            } else {
                rowColumns = changesMap.get(index);
            }
            rowColumns.put(tableCol.getText(), getValue(checkBox.isSelected()));
            System.out.println("changesMap: " + changesMap);
            ArrayList<String> removeList = new ArrayList<>();
            for (Map.Entry<String, String> column :
                    rowColumns.entrySet()) {
                boolean contains = false;
                if (column.getKey().equals("Индивид. план")) {
                    System.out.println("getTableRow().getItem().toString(): " + getTableRow().getItem().toString());
                    System.out.println("column.getValue(): " + column.getValue());
                    contains = getTableRow().getItem().toString().contains("has_ind_plan='" + column.getValue() + "'") || (column.getValue().equals("f") && getTableRow().getItem().toString().contains("has_ind_plan='null'"));
                }
                if (contains) {
                    System.out.println("remove has_ind_plan");
                    removeList.add("Индивид. план");
                    continue;
                }
                if (column.getKey().equals("Аутсорс")) {
                    contains = getTableRow().getItem().toString().contains("task_out='" + column.getValue() + "'") || (column.getValue().equals("f") && getTableRow().getItem().toString().contains("task_out='null'"));
                }
                if (contains) {
                    System.out.println("remove task_out");
                    removeList.add("Аутсорс");
                    continue;
                }

            }
            System.out.println(removeList);
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
        if (firstEdit) {
            checkBox.setSelected(getBoolean(getText()));
            firstEdit = false;
        }

        setGraphic(checkBox);
        setText(null);
    }

    private String getValue(boolean isSelected) {
        if (isSelected) {
            return "t";
        } else {
            return "f";
        }
    }

    private boolean getBoolean(String isSelected) {
        if (isSelected != null && isSelected.equals("t")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
        setText(getValue(checkBox.isSelected()));
        setItem(getValue(checkBox.isSelected()));
        // comboBox.setValue(String.valueOf(getItem()));
      /*  setGraphic(comboBox);
        setText(null);*/

    }
}
