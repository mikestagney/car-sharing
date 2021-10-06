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
    List<Car> cars;
    List<Customer> customers;

    public H2CarCompDAO(String dataSource) {
        DB_URL += dataSource;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);
            stmt = conn.createStatement();

            String drop = "DROP TABLE IF EXISTS customer";
            stmt.execute(drop);
            drop = "DROP TABLE IF EXISTS car";
            stmt.execute(drop);
            drop = "DROP TABLE IF EXISTS company";
            stmt.execute(drop);

            String sql = "CREATE TABLE company (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    " NAME VARCHAR(30) NOT NULL UNIQUE)";
            stmt.execute(sql);
            sql = "CREATE TABLE car (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(30) NOT NULL UNIQUE," +
                    "COMPANY_ID INT NOT NULL," +
                    "CONSTRAINT fk_companyID FOREIGN KEY (COMPANY_ID)" +
                    "REFERENCES company(ID))";
            stmt.execute(sql);
            sql = "CREATE TABLE customer (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(30) NOT NULL UNIQUE," +
                    "RENTED_CAR_ID INT," +
                    "CONSTRAINT fk_carRentedID FOREIGN KEY (RENTED_CAR_ID)" +
                    "REFERENCES car(ID))";
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addCompany(String name) {
        String insert = "INSERT INTO company (name) VALUES (?)";
        try {
            prepStmt = conn.prepareStatement(insert);
            prepStmt.setString(1, name);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addCar(String name, int companyId) {
        String insert = "INSERT INTO car (name, company_id) VALUES (?, ?)";
        try {
            prepStmt = conn.prepareStatement(insert);
            prepStmt.setString(1, name);
            prepStmt.setInt(2, companyId);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addCustomer(String name) {
        String insert = "INSERT INTO customer (name) VALUES (?)";
        try {
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
    @Override
    public List<Car> getAllCars(int currentCompanyId) {
        cars = new ArrayList<>();
        String select = "SELECT * FROM car WHERE company_id = ? ORDER BY id";
        try {
            prepStmt = conn.prepareStatement(select);
            prepStmt.setInt(1, currentCompanyId);
            ResultSet query =  prepStmt.executeQuery();
            while (query.next()) {
                int id = query.getInt("id");
                String name = query.getString("name");
                int companyID = query.getInt("company_id");
                Car currentCar = new Car(id, name, companyID);
                cars.add(currentCar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cars;
    }
    @Override
    public List<Customer> getAllCustomers() {
        customers = new ArrayList<>();
        String select = "SELECT * FROM customer";
        try {
            prepStmt = conn.prepareStatement(select);
            ResultSet query =  prepStmt.executeQuery();
            while (query.next()) {
                int id = query.getInt("id");
                String name = query.getString("name");
                Integer carRentalId = query.getInt("rented_car_id");
                Customer currentCustomers = new Customer(id, name, carRentalId);
                customers.add(currentCustomers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
    public void closeAndExit() {
        try {
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
