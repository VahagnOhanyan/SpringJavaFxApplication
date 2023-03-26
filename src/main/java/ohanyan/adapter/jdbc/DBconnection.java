package ohanyan.adapter.jdbc;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.*;

@Service
public class DBconnection {
    Connection c = null;
    Statement stmt = null;
    Statement serviceStmt = null;
    ResultSet rs = null;
    ResultSet serviceRs = null;

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public void openDB() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, username, password);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            serviceStmt = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void query(String sql) {
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void queryClose() {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void serviceQuery(String sql) {
        try {
            serviceRs = serviceStmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void serviceQueryClose() {
        try {
            serviceRs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            stmt.close();
            serviceStmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Statement getServiceStmt() {
        return serviceStmt;
    }

    public void setServiceStmt(Statement serviceStmt) {
        this.serviceStmt = serviceStmt;
    }

    public ResultSet getServiceRs() {
        return serviceRs;
    }

    public void setServiceRs(ResultSet serviceRs) {
        this.serviceRs = serviceRs;
    }

    public Connection getC() {
        return c;
    }

    public void setC(Connection c) {
        this.c = c;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
}

