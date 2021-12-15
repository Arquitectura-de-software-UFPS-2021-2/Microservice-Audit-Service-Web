package Auditoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Conexion de prueba.

public class ModelDb {

    private static String user = "root";
    private static String password = "";
    private static String host = "localhost";
    private static String port = "3306";
    private static String db = "gestionsistema";
    private static Connection conexion = null;

    public ModelDb() {

        loadDriver();

    }

    private void loadDriver() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException sql) {
            System.out.println(sql.getLocalizedMessage());
        }
    }

    public static Connection conexion() {

        try {

            conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, password);

        } catch (SQLException | NullPointerException sql) {
            System.out.println(sql.getLocalizedMessage());

        }

        return conexion;
    }

    public void close() {

        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException sql) {
            System.out.println(sql.getLocalizedMessage());
        }
    }
}
