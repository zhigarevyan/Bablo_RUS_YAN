package util;

import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectorDB {
    public static Connection getConnection(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db", Locale.getDefault());
        String url =resourceBundle.getString("db.url")
    }
}
