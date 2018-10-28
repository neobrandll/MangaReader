package com.NitroReader.utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {
    private DBAccess() {
    }
    private static DBAccess dbAccess = null;
    private PropertiesReader props = PropertiesReader.getInstance();
    public static DBAccess getInstance() {

        if (dbAccess == null) {
            dbAccess = new DBAccess();
        }
        return dbAccess;
    }

    public Connection createConnection(){
        Connection connection = null;
        try {
            Class.forName(props.getValue("dbDriver"));
            connection = DriverManager.getConnection(props.getValue("dbURL"),props.getValue("dbUser"),props.getValue("dbPassword"));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(props.getValue("dbError") + " " + e.getMessage());
        }
        return connection;
    }
}
