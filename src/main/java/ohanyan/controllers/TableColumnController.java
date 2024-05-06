package ohanyan.controllers;

import java.util.Arrays;

/**
 * todo Document type TableColumnController
 */
public class TableColumnController {
    private static String tableColName;
    private String tableColValue;
    private String task_id;
    private String task_name;
    private String task_number;
    private String user_fullname;
    private String user_tel;
    private String user_address;
    private String user_email;
    private String user_role_name;
    private String contract_id;
    private String contract_name;
    private String contract_number;
    private String project_id;
    private String project_name;
    private String customer_id;
    private String customer_name;
    private String customer_full_name;
    private String request_id;
    private String request_number;
    private String request_description;
    private String site_id;
    private String site_name;
    private String site_code;


    public TableColumnController() {

    }

    public void setTableColValue(String bdColName, String value) {
        tableColValue = value;
        setColumnValue(bdColName);

    }

    public void setColumnValue(String bdColName) {
        switch (bdColName) {
            case "customer_id":
                customer_id = tableColValue;
                break;
            case "customer_full_name":
                customer_full_name = tableColValue;
                break;
            case "customer_name":
                customer_name = tableColValue;
                break;
            case "request_id":
                request_id = tableColValue;
                break;
            case "request_description":
                request_description = tableColValue;
                break;
            case "request_number":
                request_number = tableColValue;
                break;
            case "task_id":
                task_id = tableColValue;
                break;
            case "task_name":
                task_name = tableColValue;
                break;
            case "task_number":
                task_number = tableColValue;
                break;
            case "project_id":
                project_id = tableColValue;
                break;
            case "project_name":
                project_name = tableColValue;
                break;
            case "contract_id":
                contract_id = tableColValue;
                break;
            case "contract_number":
                contract_number = tableColValue;
                break;
            case "contract_name":
                contract_name = tableColValue;
                break;
            case "user_fullname":
                user_fullname = tableColValue;
                break;
            case "user_tel":
                user_tel = tableColValue;
                break;
            case "user_address":
                user_address = tableColValue;
                break;
            case "user_email":
                user_email = tableColValue;
                break;
            case "user_role_name":
                user_role_name = tableColValue;
                break;
            case "site_id":
                site_id = tableColValue;
                break;
            case "site_name":
                site_name = tableColValue;
                break;
            case "site_code":
                site_code = tableColValue;
                break;
        }
    }

    public static String generateColName(String bdColName) {
        switch (bdColName) {
            case "customer_id":
                tableColName = "Customer ID";
                break;
            case "customer_full_name":
                tableColName = "Full name";
                break;
            case "customer_name":
                tableColName = "Customer";
                break;
            case "task_id":
                tableColName = "Task ID";
                break;
            case "task_name":
                tableColName = "Task name";
                break;
            case "task_number":
                tableColName = "Task number";
                break;
            case "project_id":
                tableColName = "Project ID";
                break;
            case "project_name":
                tableColName = "Project";
                break;
            case "request_id":
                tableColName = "Request ID";
                break;
            case "request_description":
                tableColName = "Request description";
                break;
            case "request_number":
                tableColName = "Request number";
                break;
            case "contract_id":
                tableColName = "Contract ID";
                break;
            case "contract_number":
                tableColName = "Contract number";
                break;
            case "contract_name":
                tableColName = "Contract name";
                break;
            case "user_fullname":
                tableColName = "Full name";
                break;
            case "user_tel":
                tableColName = "Phone";
                break;
            case "user_address":
                tableColName = "Address";
                break;
            case "user_email":
                tableColName = "Email";
                break;
            case "user_role_name":
                tableColName = "Rtle";
                break;
            case "site_id":
                tableColName = "Site ID";
                break;
            case "site_name":
                tableColName = "Site";
                break;
            case "site_code":
                tableColName = "Code";
                break;
            default:
                tableColName = "";
                break;
        }
        return tableColName;
    }

    public String getTask_id() {
        return task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getTask_number() {
        return task_number;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public String getUser_address() {
        return user_address;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_role_name() {
        return user_role_name;
    }

    public String getContract_id() {
        return contract_id;
    }

    public String getContract_name() {
        return contract_name;
    }

    public String getContract_number() {
        return contract_number;
    }

    public String getProject_id() {
        return project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_full_name() {
        return customer_full_name;
    }

    public String getRequest_id() {
        return request_id;
    }

    public String getRequest_number() {
        return request_number;
    }

    public String getRequest_description() {
        return request_description;
    }

    public String getSite_id() {
        return site_id;
    }

    public String getSite_name() {
        return site_name;
    }

    public String getSite_code() {
        return site_code;
    }

}
