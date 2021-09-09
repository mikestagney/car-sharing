package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class H2CarCompDAO implements CarDAO {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static String DB_URL = "jdbc:h2:./src/carsharing/db/";
    Connection conn = null;
    Statement stmt = null;
    List companies;

    public H2CarCompDAO(String dataSource) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);
            stmt = conn.createStatement();

            String sql = "CREATE TABLE COMPANY (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR(30)) UNIQUE NOT NULL";
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addCompany(String name) {

    }
    @Override
    public List<CarCompany> getAllCompanies() {

        return new ArrayList<>();
    }

}
