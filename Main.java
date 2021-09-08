package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static String DB_URL = "jdbc:h2:./src/carsharing/db/";   //  "jdbc:h2:file:../task/src/carsharing/db/"
    static final String USER = "JB";
    static final String PASS ="Java";


    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        String filename = "test";
        for (int i = 0; i < args.length; i++) {
            if ("-databaseFileName".equals(args[i]) && i + 1 < args.length) {
                filename = args[i + 1];
            }
        }
        DB_URL += filename;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);

            stmt = conn.createStatement();

            String sql = "CREATE TABLE COMPANY (" +
                    "ID INT, " +
                    "NAME VARCHAR(30))";
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}