package ohanyan.code.custom;

import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import ohanyan.controllers.TableColumnController;

public class TreeCell<S> extends TableCell<S, String> {
    String prevNode;
    TableColumn<TableColumnController, String> tableCol;
    private final Text newText = new Text();

    public TreeCell(TableColumn<TableColumnController, String> tableCol, String prevNode) {
        this.tableCol = tableCol;
        this.prevNode = prevNode;

    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(item);
        if (item != null && item.contains("*")) {
            Tooltip tooltip = new Tooltip();
            tooltip.setText("У данного пользователя индивидуальная настройка доступа");
            setTooltip(tooltip);
        }
        if (prevNode.equals("Мои задачи") || prevNode.equals("В работе") || prevNode.equals("Выполнено") || prevNode.equals("Проверено") ||
                prevNode.equals("Утверждено") || prevNode.equals("В ожидании")) {
            if (tableCol.getText().equals("Наименование задачи")) {
                setPrefHeight(Control.USE_COMPUTED_SIZE);
                setPrefWidth(550);
                newText.wrappingWidthProperty().bind(widthProperty());
                if (empty || item == null) {
                    newText.setText("");
                    setGraphic(newText);
                    setStyle("");
                } else {
                    newText.setText(item);
                    setGraphic(newText);
                }

            }
            System.out.println("TreeCell: " + tableCol.getText());
            if (tableCol.getText().contains("ID")) {
                System.out.println("right");
                setStyle("-fx-alignment: CENTER-RIGHT;");
            }
        }
    }

}