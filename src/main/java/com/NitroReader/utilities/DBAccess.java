package com.NitroReader.utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {
    private DBAccess() {
    }
    private static Connection connection = null;
    private static PropertiesReader props = PropertiesReader.getInstance();
    public static Connection getConnection() {
        try {
            Class.forName(props.getValue("dbDriver"));
            Connection con = DriverManager.getConnection(props.getValue("dbURL"),props.getValue("dbUser"),props.getValue("dbPassword"));
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(props.getValue("dbError") + " " + e.getMessage());
        }
        return connection;
    }
}
