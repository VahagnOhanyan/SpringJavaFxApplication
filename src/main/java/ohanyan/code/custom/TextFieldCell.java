package ohanyan.code.custom;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import ohanyan.controllers.TableColumnController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextFieldCell extends TableCell<TableColumnController, String> {


    private final TextField textField = new TextField();
    private final TreeView<String> treeView;

    TableColumn<TableColumnController, String> tableCol;
    int index;
    String prevNode;
    Map<Integer, Map<String, String>> changesMap;
    Button btnChangesApply;
    Button btnChangesCancel;

    public TextFieldCell(TreeView<String> treeView, Button btnChangesApply, Button btnChangesCancel,
                         Map<Integer, Map<String, String>> changes, TableColumn<TableColumnController,
            String> tableCol, String prevNode) {
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
        if (prevNode.equals("My tasks")) {
            if (tableCol.getText().equals("Статус")) {
                setPrefWidth(120);
            } else {
                setStyle("-fx-alignment: LEFT;");
            }
        }

    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (getTableRow() != null && getTableRow().getIndex() >= 0) {
            index = getTableRow().getIndex();

        }
        textField.textProperty().addListener(event -> {
            Map<String, String> rowColumns;
            if (!changesMap.containsKey(index)) {
                rowColumns = new HashMap<>();
                changesMap.put(index, rowColumns);
            } else {
                rowColumns = changesMap.get(index);
            }
            rowColumns.put(tableCol.getText(), textField.getText());
            System.out.println("changesMap: " + changesMap);
            ArrayList<String> removeList = new ArrayList<>();
            for (Map.Entry<String, String> column :
                    rowColumns.entrySet()) {
                boolean contains = false;
                if (column.getKey().equals("Phone")) {
                    contains = getTableRow().getItem().toString().contains("user_tel='" + column.getValue() + "'");
                }
                if (contains) {
                    System.out.println("remove user_tel");
                    removeList.add("Phone");
                    continue;
                }
                if (column.getKey().equals("Address")) {
                    contains = getTableRow().getItem().toString().contains("user_address='" + column.getValue() + "'");
                }
                if (contains) {
                    System.out.println("remove user_address");
                    removeList.add("Address");
                    continue;
                }
                if (column.getKey().equals("Email")) {
                    contains = getTableRow().getItem().toString().contains("user_email='" + column.getValue() + "'");
                }
                if (contains) {
                    System.out.println("remove user_email");
                    removeList.add("Email");
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
        if (textField.getText().equals("")) {
            textField.setText(getText());
        }

        setGraphic(textField);
        textField.setAlignment(Pos.CENTER_LEFT);
        if (tableCol.getText().equals("Тр-ть план. внешн.") || tableCol.getText().equals("Тр-ть план. внутр.") ||
                tableCol.getText().equals("Ед. факт") || tableCol.getText().equals("Ед. план")) {

            textField.textProperty().addListener((ov, oldValue, newValue) -> {
                if (!newValue.matches("^\\d*\\.?\\d*$")) {
                    textField.setText(newValue.replaceAll("[^\\d.?]", ""));
                }

            });
        }
        setText(null);

    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
        setText(textField.getText());
        setItem(textField.getText());
        // comboBox.setValue(String.valueOf(getItem()));
      /*  setGraphic(comboBox);
        setText(null);*/

    }
}
