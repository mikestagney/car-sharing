package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2CarCompDAO implements CarDAO {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static String DB_URL = "jdbc:h2:./src/carsharing/db/";
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement prepStmt = null;
    List<CarCompany> companies;

    public H2CarCompDAO(String dataSource) {
        DB_URL += dataSource;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);
            stmt = conn.createStatement();
            String drop = "DROP TABLE IF EXISTS company";
            String sql = "CREATE TABLE company (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    " NAME VARCHAR(30) NOT NULL UNIQUE)";
            stmt.execute(drop);
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addCompany(String name) {
        String insert = "INSERT INTO company (name) VALUES (?)";
        try {
            //conn = DriverManager.getConnection(DB_URL);
            prepStmt = conn.prepareStatement(insert);
            prepStmt.setString(1, name);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<CarCompany> getAllCompanies() {
        companies = new ArrayList<>();
        String select = "SELECT * FROM company ORDER BY id";
        try {
            //conn = DriverManager.getConnection(DB_URL);
            prepStmt = conn.prepareStatement(select);

            ResultSet query =  prepStmt.executeQuery();
            while (query.next()) {
                int id = query.getInt("id");
                String name = query.getString("name");
                CarCompany currentCompany = new CarCompany(id, name);
                companies.add(currentCompany);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companies;
    }
}
