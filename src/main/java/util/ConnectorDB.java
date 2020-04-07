package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectorDB {
    public static Connection getConnection() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db", Locale.getDefault());
        String url =resourceBundle.getString("db.url");
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");

        return DriverManager.getConnection(url,user,password);
    }
}
