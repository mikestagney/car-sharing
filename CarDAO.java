package carsharing;

import java.util.List;

public interface CarDAO {

    public void addCompany(String name);
    public void addCar(String name, int companyId);
    public void rentCar(Customer customer, int companyId);
    public void returnCar(Customer customer, int companyId);
    public void addCustomer(String name);
    public List<CarCompany> getAllCompanies();
    public List<Car> getAllCars(int currentCompanyId);
    public List<Customer> getAllCustomers();
    public void closeAndExit();

}
