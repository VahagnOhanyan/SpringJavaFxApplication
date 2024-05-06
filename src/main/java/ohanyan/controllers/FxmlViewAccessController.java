package ohanyan.controllers;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

@Component
public class FxmlViewAccessController {

    public static void resolveAccess(Node node) {
        if (node instanceof MenuBar) {
            MenuBar menuBar = (MenuBar) node;
            for (Menu menu : menuBar.getMenus()) {
                for (MenuItem menuItem : menu.getItems()) {
                    if (menuItem.getId() != null) {
                        for (String key : ContextController.authorities.keySet()) {
                            if (key.contains(menuItem.getId())) {
                                int accessType = ContextController.authorities.get(key);
                                if (accessType == 4) {
                                    menuItem.setVisible(false);
                                    break;
                                }
                                if (accessType == 1) {
                                    menuItem.setVisible(true);
                                    break;
                                }
                            }

                        }
                    }
                }
            }
        }


        if (node instanceof ToolBar) {
            ToolBar toolBar = (ToolBar) node;
            for (Node n : toolBar.getItems()) {
                if (n instanceof Parent) {
                    boolean hasPrivilege = false;
                    if (n.getId() != null) {
                        for (String key : ContextController.authorities.keySet()) {
                            if (key.contains(n.getId())) {
                                hasPrivilege = true;
                                int accessType = ContextController.authorities.get(key);
                                if (accessType == 4) {
                                    n.setVisible(false);
                                    break;
                                }
                                if (accessType == 1) {
                                    n.setVisible(true);
                                    Parent parent = (Parent) n;
                                    resolveAccess(parent);
                                    break;
                                }
                            }
                            if (!hasPrivilege) {
                                Parent parent = (Parent) n;
                                resolveAccess(parent);
                            }
                        }
                    } else {
                        Parent parent = (Parent) n;
                        resolveAccess(parent);
                    }
                }
            }
        }

        if (node instanceof Button) {
            if (node.getId() != null) {
                for (String key : ContextController.authorities.keySet()) {
                    if (key.contains(node.getId())) {
                        int accessType = ContextController.authorities.get(key);
                        if (accessType == 1) {
                            node.setDisable(false);
                            break;
                        }
                        if (accessType == 4) {
                            node.setDisable(true);
                            break;
                        }
                    }
                }
            }
        }


        if (node instanceof MenuButton) {
            MenuButton menuButton = (MenuButton) node;
            for (MenuItem menuItem : menuButton.getItems()) {
                if (menuItem.getId() != null) {
                    for (String key : ContextController.authorities.keySet()) {
                        if (key.contains(menuItem.getId())) {
                            int accessType = ContextController.authorities.get(key);
                            if (accessType == 4) {
                                menuItem.setVisible(false);
                                break;
                            }
                            if (accessType == 1) {
                                menuItem.setVisible(true);
                                break;
                            }
                        }
                    }
                }
            }
            disableEmptyMenuButton(menuButton);
        }
        if (node instanceof SplitPane) {
            SplitPane splitPane = (SplitPane) node;
            for (Node n : splitPane.getItems()) {
                if (n instanceof Parent) {
                    if (n.getId() != null) {
                        boolean hasPrivilege = false;
                        for (String key : ContextController.authorities.keySet()) {
                            if (key.contains(n.getId())) {
                                hasPrivilege = true;
                                int accessType = ContextController.authorities.get(key);
                                if (accessType == 4) {
                                    n.setVisible(false);
                                    break;
                                }
                                if (accessType == 1) {
                                    n.setVisible(true);
                                    Parent parent = (Parent) n;
                                    resolveAccess(parent);
                                    break;
                                }
                            }

                        }
                        if (!hasPrivilege) {
                            Parent parent = (Parent) n;
                            resolveAccess(parent);
                        }
                    } else {
                        Parent parent = (Parent) n;
                        resolveAccess(parent);
                    }
                }
            }
        }


        if (node instanceof TreeView) {
            boolean hasPrivilege = false;
            if (node.getId() != null) {
                for (String k : ContextController.authorities.keySet()) {
                    if (k.contains(node.getId())) {
                        hasPrivilege = true;
                        int accessType = ContextController.authorities.get(k);
                        if (accessType == 4) {
                            node.setVisible(false);
                            break;
                        }
                        if (accessType == 1) {
                            node.setVisible(true);
                            TreeView<String> treeView = (TreeView) node;
                            TreeItem<String> treeItem = treeView.getTreeItem(0);
                            resolveAccess(treeItem);
                            deleteLeafItems(treeItem);
                            break;
                        }
                    }
                    if (!hasPrivilege) {
                        TreeView<String> treeView = (TreeView) node;
                        TreeItem<String> treeItem = treeView.getTreeItem(0);
                        resolveAccess(treeItem);
                        deleteLeafItems(treeItem);
                    }
                }
            } else {
                TreeView<String> treeView = (TreeView) node;
                TreeItem<String> treeItem = treeView.getTreeItem(0);
                resolveAccess(treeItem);
                deleteLeafItems(treeItem);
            }
        }
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node n : parent.getChildrenUnmodifiable()) {
                if (n instanceof Parent) {
                    Parent parent1 = (Parent) n;
                    resolveAccess(parent1);
                }
            }
        }

    }

    private static void disableEmptyMenuButton(Node node) {
        if (node instanceof MenuButton) {
            for (MenuItem item :
                    ((MenuButton) node).getItems()) {
                if (item.isVisible() && item.getId() != null && !item.getId().equals("")) {
                    node.setDisable(false);
                    break;
                }
                node.setDisable(true);
            }
        }
    }

    private static void deleteLeafItems(TreeItem<String> treeItem) {
        for (int i = 0; i < treeItem.getChildren().size(); i++) {
            if (treeItem.getChildren().get(i).isLeaf()) {
                if (!treeItem.getChildren().get(i).getValue().equals("Timesheet")) {
                    treeItem.getChildren().remove(treeItem.getChildren().get(i));
                    --i;
                }

            }
        }

    }

    public static void resolveAccess(TreeItem<String> rootItem) {
        for (int i = 0; i < rootItem.getChildren().size(); i++) {
            String value = rootItem.getChildren().get(i).getValue();
            TreeItem<String> child = rootItem.getChildren().get(i);
            for (String key : ContextController.authorities.keySet()) {
                if (key.contains(getTranslatedValue(value)) && key.startsWith("read")) {
                    int accessType = ContextController.authorities.get(key);
                    if (accessType == 4) {
                        rootItem.getChildren().remove(rootItem.getChildren().get(i));
                        --i;
                        break;
                    }
                }
            }
            if (!child.getChildren().isEmpty()) {
                resolveAccess(child);
            }


        }
    }

    public static CharSequence getTranslatedValue(String value) {
        String v = value;
        switch (value) {
            case "Management":
                v = "Management";
                break;
            case "My tasks":
            case "Tasks":
            case "Task":
                v = "Task";
                break;
            case "Directories":
            case "Directory":
                v = "Reference";
                break;
            case "Contracts":
            case "Contract":
                v = "Contract";
                break;
            case "Requests":
            case "Request":
                v = "Request";
                break;
            case "My projects":
            case "Projects":
            case "Project":
                v = "Project";
                break;
            case "Customer":
            case "Customers":
                v = "Customer";
                break;
            case "Site":
            case "Sites":
                v = "Site";
                break;
            case "Employee":
            case "Employees":
                v = "Employee";
                break;
            case "Timesheet":
            case "TimeSheet":
                v = "Timesheet";
                break;
            case "Report":
            case "Reports":
                v = "";
                break;
            case "Csv":
                v = "Csv";
                break;
        }
        return v;
    }
}
