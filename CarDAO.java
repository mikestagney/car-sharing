package carsharing;

import java.util.List;

public interface CarDAO {

    public void addCompany(String name);
    public void addCar(String name, int companyId);
    public void addCustomer(String name);
    public List<CarCompany> getAllCompanies();
    public List<Car> getAllCars(int currentCompanyId);
    public void closeAndExit();

}
