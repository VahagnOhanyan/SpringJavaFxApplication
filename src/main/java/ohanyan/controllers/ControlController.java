package ohanyan.controllers;

/**
 * todo Document type TableColumnController
 */
public class ControlController {


    private static String type = "";


    public static String defineColumnType(String prevNode, String columnName) {
        if (prevNode.equals("My tasks")) {
            switch (columnName) {
                case "Phone":
                case "Address":
                case "Email":
                    type = "TextField";
                    break;
                default:
                    type = "";
            }
        }
        return type;
    }
}
